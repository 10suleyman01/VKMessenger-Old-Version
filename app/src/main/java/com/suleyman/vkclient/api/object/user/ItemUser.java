package com.suleyman.vkclient.api.object.user;

import com.google.gson.annotations.*;

public class ItemUser {
	
	@SerializedName("id")
	@Expose
	private String id;
	
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

	public ItemUser(String id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getOnline() {
		return online;
	}
	
	public String getTitle() {
		return String.format("%s %s", firstName, lastName);
	}
}
