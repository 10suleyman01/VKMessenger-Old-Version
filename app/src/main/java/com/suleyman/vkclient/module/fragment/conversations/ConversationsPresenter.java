package com.suleyman.vkclient.module.fragment.conversations;

import io.reactivex.functions.*;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.suleyman.vkclient.api.http.callable.GetConversationsCallable;
import com.suleyman.vkclient.api.object.conversations.ObjectConversations;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class ConversationsPresenter extends MvpBasePresenter<ConversationsView> {
	
	private Disposable disposable;
	
	public void loadConversations() {
		final Flowable<ObjectConversations> flowConvs = Flowable.fromCallable(new GetConversationsCallable());
			disposable = flowConvs.delay(300, TimeUnit.MILLISECONDS, Schedulers.io()).
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
						getView().showError(error);
					}
				}, new Action() {
					@Override
					public void run() throws Exception {
						if (!isViewAttached()) return;
						getView().showLoading(false);
					}
				});
	}
	
	public void dispose() {
		if (disposable != null && !disposable.isDisposed()) {
			disposable.dispose();
		}
	}
	
}
