package com.suleyman.vkclient.api.object.user;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class ObjectUser {
	
	@SerializedName("response")
	@Expose
	private ArrayList<ItemUser> response;
	
	public void setResponse(ArrayList<ItemUser> response) {
		this.response = response;
	}

	public ArrayList<ItemUser> getResponse() {
		return response;
	}
}
