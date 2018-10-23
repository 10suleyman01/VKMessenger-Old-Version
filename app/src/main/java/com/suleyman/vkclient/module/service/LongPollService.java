package com.suleyman.vkclient.module.service;

import com.suleyman.vkclient.api.http.callable.*;
import com.suleyman.vkclient.api.object.longPollServer.*;
import com.suleyman.vkclient.util.*;
import io.reactivex.functions.*;
import org.greenrobot.eventbus.*;
import org.json.*;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.suleyman.vkclient.api.event.TypingEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class LongPollService extends Service {
	
	private static int NEW_MESSAGE = 4;
	private static int TYPING_MESSAGE = 61;
	private static int TYPING_MESSAGE_CHAT = 62;
	
	@Override
	public void onCreate() {
		super.onCreate();

		UEventBus.register(this);
	}

	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	public void onServer(ResponseLongPoll response) {
		final LongPollServerCallable longPollServerCallable = new LongPollServerCallable(response.getServer(), response.getKey(), response.getTs());

		DisposableManager.add("long_poll_server",
							  Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.io()).
							  flatMap(new Function<Long, Observable<String>>(){
									  @Override
									  public Observable<String> apply(Long tick) throws Exception {
										  return Observable.just(longPollServerCallable.call());
									  }			
								  }).
							  retry().
							  subscribeOn(Schedulers.io()).
							  observeOn(AndroidSchedulers.mainThread()).
							  subscribe(new Consumer<String>() {
									  @Override
									  public void accept(String updatesJson) throws Exception {
										  JSONObject object = new JSONObject(updatesJson);

										  JSONArray updates = object.getJSONArray("updates");

										  for (int i = 0; i < updates.length(); i++) {
											  JSONArray update = (JSONArray) updates.get(i);
											  int updateCode = update.getInt(0);
											  if (updateCode == TYPING_MESSAGE) {
												  long id = update.getInt(1);
												  UEventBus.post(new TypingEvent(id));
											  }
										  }
										  
										  longPollServerCallable.update(object.getLong("ts"));

									  }		
								  }, new Consumer<Throwable>(){
									  @Override
									  public void accept(Throwable error) throws Exception {
										  error.printStackTrace();
									  }		
								  }));
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		DisposableManager.add("get_longpoll_server", getLongPollServer());
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

		UEventBus.unregister(this);

		DisposableManager.dispose("get_longpoll_server");
		DisposableManager.dispose("long_poll_server");
	}

}
