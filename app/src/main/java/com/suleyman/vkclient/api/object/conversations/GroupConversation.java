package com.suleyman.vkclient.api.object.conversations;

import com.google.gson.annotations.*;

public class GroupConversation {
	

	@SerializedName("id")
	@Expose
	private long id;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("photo_200_orig")
	@Expose
	private String photo;

	@SerializedName("type")
	@Expose
	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	@Override
	public String toString() {
		return String.format("%s\n%s\n%s\n%s", name, photo, id, type);
	}
}
