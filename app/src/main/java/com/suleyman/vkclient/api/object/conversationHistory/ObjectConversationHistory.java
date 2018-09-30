package com.suleyman.vkclient.api.object.conversationHistory;

import com.google.gson.annotations.*;

import com.suleyman.vkclient.api.object.conversations.ResponseConversations;

public class ObjectConversationHistory {
	
	@SerializedName("response")
	@Expose
	private ResponseConversationHistory response;

	public void setResponse(ResponseConversationHistory response) {
		this.response = response;
	}

	public ResponseConversationHistory getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return String.format("%s", response);
	}
}
