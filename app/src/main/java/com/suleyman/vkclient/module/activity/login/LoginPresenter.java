package com.suleyman.vkclient.module.activity.login;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.suleyman.vkclient.api.ObservableProvider;
import com.suleyman.vkclient.api.http.callable.LoginCallable;
import com.suleyman.vkclient.api.object.account.VKAccount;
import com.suleyman.vkclient.util.DisposableManager;
import io.reactivex.observers.DisposableObserver;

public class LoginPresenter extends MvpBasePresenter<LoginView> {


	public void login(String email, String password) {
		DisposableManager.add("login",
							  ObservableProvider.observableFromCallable(new LoginCallable(email, password)).
							  subscribeWith(new DisposableObserver<VKAccount>(){
									  @Override
									  public void onNext(VKAccount account) {
										  if (isViewAttached()) {

											  String error = account.getError();
											  if (error != null && !error.isEmpty()) {
												  getView().showDialogError(account.getErrorDescription());
											  } else {
												  getView().showDialogLoading(true);
												  getView().setAccount(account);
												  getView().toMain();
											  }

										  }
									  }

									  @Override
									  public void onError(Throwable error) {
										  if (isViewAttached()) {
											  getView().showDialogError(error.getMessage());
										  }
									  }

									  @Override
									  public void onComplete() {
										  if (isViewAttached()) {

										  }
									  }
								  })
							  );
	}

	public void dispose() {
		DisposableManager.dispose("login");
	}
}
