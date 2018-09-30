package com.suleyman.vkclient.api.object.conversations;

import com.google.gson.annotations.*;

public class ObjectConversations {
	
	@SerializedName("response")
	@Expose
	private ResponseConversations response;

	private static String mData;

	public static void setData(String data) {
		mData = data;
	}

	public String getData() {
		return mData;
	}

	public void setResponse(ResponseConversations response) {
		this.response = response;
	}

	public ResponseConversations getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return String.format("%s", response);
	}
}
