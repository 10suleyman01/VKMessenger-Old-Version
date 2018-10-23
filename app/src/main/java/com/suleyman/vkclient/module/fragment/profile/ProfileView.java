package com.suleyman.vkclient.module.fragment.profile;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.suleyman.vkclient.api.object.user.ObjectUser;

public interface ProfileView extends MvpView {
	
	public void showProfile(ObjectUser user)
	public void showError(Throwable error);
	
}
