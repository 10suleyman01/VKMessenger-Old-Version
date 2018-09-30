package com.suleyman.vkclient.api.object.friends;

import com.google.gson.annotations.*;

public class ObjectFriends {
	
	@SerializedName("response")
	@Expose
	private ResponseFriends response;

	public void setResponse(ResponseFriends response) {
		this.response = response;
	}

	public ResponseFriends getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return getResponse().toString();
	}
}
