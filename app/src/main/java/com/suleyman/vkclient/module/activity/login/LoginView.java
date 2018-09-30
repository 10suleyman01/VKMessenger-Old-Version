package com.suleyman.vkclient.module.activity.login;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.suleyman.vkclient.api.object.account.VKAccount;

public interface LoginView extends MvpView {
	
	void showDialogLoading(boolean loading);
	void setAccount(VKAccount account);
	void showDialogError(String message);
	void toMain();
}
