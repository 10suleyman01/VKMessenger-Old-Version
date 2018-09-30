package com.suleyman.vkclient.api.object.account;

import com.google.gson.annotations.*;

public class VKAccount {
	
	@SerializedName("access_token")
	@Expose
	private String token;

	@SerializedName("user_id")
	@Expose
	private long userId;
	
	@SerializedName("error")
	@Expose
	private String error;
	
	@SerializedName("error_description")
	@Expose
	private String errorDescription;

	public String getError() {
		return error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public String getToken() {
		return token;
	}

	public long getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return String.format("%s/n%s", getToken(), getUserId());
	}
}
