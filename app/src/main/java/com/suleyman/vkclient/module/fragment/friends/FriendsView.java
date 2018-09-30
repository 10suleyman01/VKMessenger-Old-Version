package com.suleyman.vkclient.module.fragment.friends;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.suleyman.vkclient.api.object.friends.ObjectFriends;

public interface FriendsView extends MvpView {
	
	void showLoading(boolean loading);
	void setFriends(ObjectFriends objectFriends);
	void showError(String message);
}
