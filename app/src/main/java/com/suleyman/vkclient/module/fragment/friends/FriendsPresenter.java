package com.suleyman.vkclient.module.fragment.friends;

import io.reactivex.functions.*;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.suleyman.vkclient.api.http.callable.GetFriendsCallable;
import com.suleyman.vkclient.api.object.friends.ObjectFriends;
import com.suleyman.vkclient.util.DisposableManager;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FriendsPresenter extends MvpBasePresenter<FriendsView> {

	public void loadFriends() {
		DisposableManager.add(
			Flowable.fromCallable(new GetFriendsCallable()).
			subscribeOn(Schedulers.io()).
			observeOn(AndroidSchedulers.mainThread()).
			subscribe(new Consumer<ObjectFriends>(){
					@Override
					public void accept(ObjectFriends objectFriends) throws Exception {
						if (!isViewAttached()) return;
						getView().showLoading(true);
						getView().setFriends(objectFriends);
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
				})

		);

	}
}
