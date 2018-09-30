package com.suleyman.vkclient.api.object.longPollServer;

import com.google.gson.annotations.*;

public class ObjectLongPollServer {

	@SerializedName("response")
	@Expose
	private ResponseLongPoll response;

	public void setResponse(ResponseLongPoll response) {
		this.response = response;
	}

	public ResponseLongPoll getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return response.toString();
	}
	
}
