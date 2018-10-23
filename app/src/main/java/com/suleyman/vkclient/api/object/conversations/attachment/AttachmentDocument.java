package com.suleyman.vkclient.api.object.conversations.attachment;

import com.google.gson.annotations.*;

public class AttachmentDocument {
	
	@SerializedName("id")
	@Expose
	private long id;
	
	@SerializedName("owner_id")
	@Expose
	private long ownerId;
	
	@SerializedName("title")
	@Expose
	private String title;
	
	@SerializedName("size")
	@Expose
	private int size;
	
	@SerializedName("ext")
	@Expose
	private String ext;
	
	@SerializedName("url")
	@Expose
	private String url;
	
	@SerializedName("date")
	@Expose
	private long date;
	
	@SerializedName("type")
	@Expose
	private int type;
	
	@SerializedName("access_key")
	@Expose
	private String accessKey;
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getExt() {
		return ext;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getDate() {
		return date;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getAccessKey() {
		return accessKey;
	}
}
