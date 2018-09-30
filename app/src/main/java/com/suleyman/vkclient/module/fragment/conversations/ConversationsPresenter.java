package com.suleyman.vkclient.module.fragment.conversations;

import io.reactivex.functions.*;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.suleyman.vkclient.api.http.callable.GetConversationsCallable;
import com.suleyman.vkclient.api.object.conversations.ObjectConversations;
import com.suleyman.vkclient.util.DisposableManager;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ConversationsPresenter extends MvpBasePresenter<ConversationsView> {
	
	public void loadConversations() {

		DisposableManager.add(
			Flowable.fromCallable(new GetConversationsCallable()).
			subscribeOn(Schedulers.io()).
			observeOn(AndroidSchedulers.mainThread()).
			subscribe(new Consumer<ObjectConversations>(){
					@Override
					public void accept(ObjectConversations objectConversations) throws Exception {
						if (!isViewAttached()) return;
						getView().showLoading(true);
						getView().setConversations(objectConversations);
					}
				}, new Consumer<Throwable>(){
					@Override
					public void accept(Throwable error) throws Exception {
						if (!isViewAttached()) return;
						getView().showLoading(false);
						getView().showError(error.getMessage());
					}
				}, new Action() {
					@Override
					public void run() throws Exception {
						if (!isViewAttached()) return;
						getView().showLoading(false);
					}
				})
		);
	}
	
}
