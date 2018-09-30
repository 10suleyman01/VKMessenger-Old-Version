package com.suleyman.vkclient.module.service;

import com.suleyman.vkclient.api.http.callable.*;
import com.suleyman.vkclient.api.object.longPollServer.*;
import com.suleyman.vkclient.util.*;
import io.reactivex.functions.*;
import java.util.*;
import org.greenrobot.eventbus.*;
import org.json.*;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class LongPollService extends Service {
	
	private Map<Long, String> CONTAINER_MESSAGE = new HashMap<>();

	private Disposable disposable;

	@Override
	public void onCreate() {
		super.onCreate();

		UEventBus.register(this);
	}

	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	public void onServer(ResponseLongPoll response) {
		final LongPollServerCallable longPollServerCallable = new LongPollServerCallable(response.getServer(), response.getKey(), response.getTs());

		final Observable<String> observ = Observable.fromCallable(longPollServerCallable).
			delay(1, TimeUnit.SECONDS, Schedulers.io()).
			repeat().		
			subscribeOn(Schedulers.io()).
			observeOn(AndroidSchedulers.mainThread());

		disposable = observ.retryWhen(new Function<Observable<Throwable>, Observable<String>>(){
				@Override
				public Observable<String> apply(Observable<Throwable> error) throws Exception {			
					return error.flatMap(new Function<Throwable, Observable<String>>(){
							@Override
							public Observable<String> apply(Throwable error) throws Exception {

								if (error instanceof SocketTimeoutException || error instanceof InterruptedException) {
									return observ.retry();
								}

								return Observable.error(error);
							}					
						}); 
				}		
			}).
			subscribe(new Consumer<String>() {
				@Override
				public void accept(String updatesJson) throws Exception {
					JSONObject object = new JSONObject(updatesJson);

					JSONArray updates = object.getJSONArray("updates");

					for (int i = 0; i < updates.length(); i++) {
						JSONArray update = (JSONArray) updates.get(i);
						int updateCode = update.getInt(0);
						if (updateCode == 4) {
							int messageId = update.getInt(1);
							String message = update.getString(5);
							//UNotificationManager.send(messageId, message);
						}
					}

					long ts = object.getLong("ts");
					longPollServerCallable.setTs(ts);
				}		
			}, new Consumer<Throwable>(){
				@Override
				public void accept(Throwable error) throws Exception {
					error.printStackTrace();
				}		
			});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		DisposableManager.add(getLongPollServer());
		return START_NOT_STICKY;
	}

	public Disposable getLongPollServer() {
		return Observable.fromCallable(new GetLongPollObjectCallable()).
			subscribeOn(Schedulers.io()).
			subscribeWith(new DisposableObserver<ObjectLongPollServer>(){
				@Override
				public void onNext(ObjectLongPollServer objectServer) {
					UEventBus.post(objectServer.getResponse());
				}

				@Override
				public void onError(Throwable error) {
					error.printStackTrace();
				}

				@Override
				public void onComplete() {

				}
			});
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if (disposable != null && !disposable.isDisposed()) {
			disposable.dispose();
			
			/** dispose polling server  */
		}

		UEventBus.unregister(this);

		DisposableManager.dispose();
	}

}
