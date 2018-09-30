package com.suleyman.vkclient.api.object.conversations;

import com.google.gson.annotations.*;

public class ProfileConversation {
	
	@SerializedName("id")
	@Expose
	private long id;

	@SerializedName("first_name")
	@Expose
	private String firstName;

	@SerializedName("last_name")
	@Expose
	private String lastName;

	@SerializedName("photo_200_orig")
	@Expose
	private String photo;

	@SerializedName("online")
	@Expose
	private int online;

	@SerializedName("sex")
	@Expose
	private int sex;

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getSex() {
		return sex;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return String.format("%s %s", firstName, lastName);
	}

	public boolean isOnline() {
		return online == 1;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	@Override
	public String toString() {
		return getTitle();
	}
}
