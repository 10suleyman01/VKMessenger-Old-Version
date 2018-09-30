package com.suleyman.vkclient.module.activity.conversation;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.suleyman.vkclient.api.object.conversationHistory.ObjectConversationHistory;

public interface ConversationView extends MvpView {
	
	void showLoading(boolean isLoading);
	void setMessages(ObjectConversationHistory objectMessages);
	void showError(String message);
}
