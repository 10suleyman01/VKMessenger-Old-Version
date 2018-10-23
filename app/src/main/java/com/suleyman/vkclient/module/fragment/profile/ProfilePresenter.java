package com.suleyman.vkclient.module.fragment.profile;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.suleyman.vkclient.api.ObservableProvider;
import com.suleyman.vkclient.api.http.callable.GetUserCallable;
import com.suleyman.vkclient.api.object.account.VKAccountManager;
import io.reactivex.functions.Consumer;
import com.suleyman.vkclient.api.object.user.ObjectUser;
import com.suleyman.vkclient.util.DisposableManager;

public class ProfilePresenter extends MvpBasePresenter<ProfileView> {

	public void loadProfile() {
		DisposableManager.add("load_profile",
							  ObservableProvider.observableFromCallable(new GetUserCallable(VKAccountManager.getUserId())).
							  subscribe(new Consumer<ObjectUser>(){
									  @Override
									  public void accept(ObjectUser objectUser) throws Exception {
										  if (!isViewAttached()) return;

										  getView().showProfile(objectUser);
									  }
								  }, new Consumer<Throwable>(){

									  @Override
									  public void accept(Throwable error) throws Exception {
										  if (!isViewAttached()) return;

										  getView().showError(error);
									  }
								  }));

	}
	
	public void dispose() {
		DisposableManager.dispose("load_profile");
	}

}
