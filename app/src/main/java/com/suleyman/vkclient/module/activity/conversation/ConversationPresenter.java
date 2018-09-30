package com.suleyman.vkclient.module.activity.conversation;

import io.reactivex.functions.*;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.suleyman.vkclient.api.http.callable.GetConversationHistoryCallable;
import com.suleyman.vkclient.api.object.conversationHistory.ObjectConversationHistory;
import com.suleyman.vkclient.util.DisposableManager;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ConversationPresenter extends MvpBasePresenter<ConversationView> {

	public void loadMessages(long peerId, int offset) {
		Flowable.fromCallable(new GetConversationHistoryCallable(peerId, offset)).
			subscribeOn(Schedulers.io()).
			observeOn(AndroidSchedulers.mainThread()).
			subscribe(new Consumer<ObjectConversationHistory>(){

				@Override
				public void accept(ObjectConversationHistory objectMessages) throws Exception {
					if (!isViewAttached()) return;
					getView().showLoading(true);
					getView().setMessages(objectMessages);
				}
			}, new Consumer<Throwable>(){
				@Override
				public void accept(Throwable error) throws Exception {
					if (!isViewAttached()) return;
					getView().showLoading(false);
					getView().showError(error.getMessage());
				}
			}, new Action(){
				@Override
				public void run() throws Exception {
					if (!isViewAttached()) return;
					getView().showLoading(false);
				}
			});
	}
	
	public void dispose() {
		DisposableManager.dispose();
	}

}
