package com.suleyman.vkclient.module.activity.login;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.suleyman.vkclient.api.http.VKRestClient;
import com.suleyman.vkclient.api.object.account.VKAccount;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import com.suleyman.vkclient.util.DisposableManager;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

	private static final String REDIRECT_URI = "https://oauth.vk.com/blank.html";
	private String urlLogin;

	public void login(String email, String password) {

		urlLogin = getUrl(email, password);

		DisposableManager.add(
			Observable.fromCallable(new Callable<VKAccount>(){
					@Override
					public VKAccount call() throws Exception {
						String data = VKRestClient.doGet(urlLogin);
						return new Gson().fromJson(data, VKAccount.class);
					}
				}).
			subscribeOn(Schedulers.io()).
			observeOn(AndroidSchedulers.mainThread()).
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

	private String getUrl(String email, String password) {
		StringBuilder urlBuilder = new StringBuilder("https://oauth.vk.com/token?grant_type=password");
		urlBuilder.append("&client_id=3697615");
		urlBuilder.append("&client_secret=AlVXZFMUqyrnABp8ncuU");
		urlBuilder.append("&username=" + email);
		urlBuilder.append("&password=" + password);
		urlBuilder.append("&redirect_uri=" + REDIRECT_URI);
		urlBuilder.append("&scope=" + getScope());
		urlBuilder.append(VKRestClient.API_VERSION);
		urlBuilder.append("&2fa_supported=1");
		return urlBuilder.toString();
	}

	private String getScope() {
		return "notify,friends,photos,audio,video,docs,status,notes,pages,wall,groups,messages,offline,notifications";
	}
}
