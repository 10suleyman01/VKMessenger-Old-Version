package com.suleyman.vkclient.api.object.conversations.longpoll;

import com.google.gson.annotations.*;

public class ObjectLongPollHistory {
	@SerializedName("response")
	@Expose
	private ResponseLongPollHistory responseHistory;

	public void setResponseHistory(ResponseLongPollHistory responseHistory) {
		this.responseHistory = responseHistory;
	}

	public ResponseLongPollHistory getResponseHistory() {
		return responseHistory;
	}
}
