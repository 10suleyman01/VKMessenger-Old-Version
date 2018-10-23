package com.suleyman.vkclient.module.service;

import com.suleyman.vkclient.api.event.*;
import com.suleyman.vkclient.api.object.conversations.*;
import com.suleyman.vkclient.api.object.conversations.longpoll.*;
import io.reactivex.functions.*;
import org.greenrobot.eventbus.*;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.suleyman.vkclient.api.VKUpdatesHandler;
import com.suleyman.vkclient.api.http.callable.GetLongPollHistoryCallable;
import com.suleyman.vkclient.api.object.longPollServer.ResponseLongPoll;
import com.suleyman.vkclient.util.UEventBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LongPollServerConversation extends Service {

	private Disposable disposable;

	@Override
	public void onCreate() {
		super.onCreate();

		UEventBus.register(this);
	}

	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	public void onServer(ResponseLongPoll response) {

		final GetLongPollHistoryCallable longPollHistoryCallable = new GetLongPollHistoryCallable(response.getTs(), response.getPts());

		final Observable<ObjectLongPollHistory> observObjectLongPollHistory = getLongPollHistory(longPollHistoryCallable);

		if (disposable == null || disposable.isDisposed()) {

			disposable = observObjectLongPollHistory.subscribe(new Consumer<ObjectLongPollHistory>(){
					@Override
					public void accept(ObjectLongPollHistory objectLongPollHistory) throws Exception {
						ResponseLongPollHistory responseHistory = objectLongPollHistory.getResponseHistory();

						if (responseHistory != null) {

							int[][] history = responseHistory.getHistory();

							if (history != null && history.length > 0) {

								for (int i = 0; i < history.length; i++) {
									int[] update = history[i];
									int updateCode = update[0];

									if (updateCode == 4) {
										
										ArrayList<SubItemConversation> conversations = responseHistory.getConversations();
										ArrayList<ConversationLastMessage> items = responseHistory.getMessages().getItems();

										int sizeConversations= conversations != null && conversations.size() > 0 ? conversations.size() : 0;
										int sizeMessages = items != null && items.size() > 0 ? items.size() : 0;

										SubItemConversation conversation = conversations.get(sizeConversations == 0 ? 0 : sizeConversations - 1);
										ConversationLastMessage message = items.get(sizeMessages == 0 ? 0 : sizeMessages - 1);


										UEventBus.post(new UpdateMessageEvent(conversation, message));
									}
								}

								UEventBus.post(new UpdatePtsEvent(responseHistory.getNewPts()));
							}
						}
					}
				}, new Consumer<Throwable>(){

					@Override
					public void accept(Throwable error) throws Exception {
						error.printStackTrace();
					}
				});
		}
	}

	public Observable<ObjectLongPollHistory> getLongPollHistory(final GetLongPollHistoryCallable longPollHistoryCallable) {
		return Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.io()).
			flatMap(new Function<Long, Observable<ObjectLongPollHistory>>(){
				@Override
				public Observable<ObjectLongPollHistory> apply(Long tick) throws Exception {
					return Observable.just(longPollHistoryCallable.call());
				}		
			}).
			retry().
			subscribeOn(Schedulers.io()).
			observeOn(AndroidSchedulers.mainThread());

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		UEventBus.unregister(this);

		if (disposable != null && !disposable.isDisposed()) {
			disposable.dispose();
		}
	}

}
