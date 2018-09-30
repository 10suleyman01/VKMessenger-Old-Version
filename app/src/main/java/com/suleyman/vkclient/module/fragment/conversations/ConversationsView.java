package com.suleyman.vkclient.module.fragment.conversations;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.suleyman.vkclient.api.object.conversations.ObjectConversations;

public interface ConversationsView extends MvpView {
	
	void showLoading(boolean loading);
	void setConversations(ObjectConversations objectConversations);
	void showError(String message);
	
}
