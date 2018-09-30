package com.suleyman.vkclient.api.object.friends;

import com.google.gson.annotations.*;

	public class ItemFriend {
	
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
	private long online;

	@SerializedName("online_app")
	@Expose
	private String onlineApp;

	@SerializedName("online_mobile")
	@Expose
	private long onlineMobile;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return String.format("%s %s", firstName, lastName);
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setOnline(long online) {
		this.online = online;
	}

	public boolean isOnline() {
		return online == 1;
	}

	public void setOnlineApp(String onlineApp) {
		this.onlineApp = onlineApp;
	}

	public String getOnlineApp() {
		return onlineApp;
	}

	public void setOnlineMobile(long onlineMobile) {
		this.onlineMobile = onlineMobile;
	}

	public long getOnlineMobile() {
		return onlineMobile;
	}

	@Override
	public String toString() {
		return getTitle();
	}
}
