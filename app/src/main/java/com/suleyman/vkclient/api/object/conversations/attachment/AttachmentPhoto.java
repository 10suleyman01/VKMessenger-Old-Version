package com.suleyman.vkclient.api.object.conversations.attachment;

import com.google.gson.annotations.*;
import java.util.*;
import com.suleyman.vkclient.api.object.conversations.attachment.AttachmentPhoto.PhotoSize;

public class AttachmentPhoto {

	@SerializedName("id")
	@Expose
	private long id;

	@SerializedName("album_id")
	@Expose
	private long albumId;

	@SerializedName("owner_id")
	@Expose
	private long ownerId;

	@SerializedName("sizes")
	@Expose
	private ArrayList<PhotoSize> sizes;

	@SerializedName("text")
	@Expose
	private String text;

	@SerializedName("date")
	@Expose
	private long date;

	@SerializedName("access_key")
	@Expose
	private String accessKey;
	
	private Map<String, String> PHOTOS = new HashMap<>();

	public String getUrl() {
		ArrayList<AttachmentPhoto.PhotoSize> urls = getSizes();
		int size = urls.size();
		return urls.get(size-1).getUrl();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public long getAlbumId() {
		return albumId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setSizes(ArrayList<PhotoSize> sizes) {
		this.sizes = sizes;
	}

	public ArrayList<PhotoSize> getSizes() {
		return sizes;
	}
	
	public String getPhoto(String type) {
		if (PHOTOS.containsKey(type)) {
			return PHOTOS.get(type);
		}
		return PHOTOS.get("xs");
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getDate() {
		return date;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public class PhotoSize {
		
		@SerializedName("type")
		@Expose
		private String type;

		@SerializedName("url")
		@Expose
		private String url;

		@SerializedName("width")
		@Expose
		private int width;

		@SerializedName("height")
		@Expose
		private int height;
		
		public PhotoSize() {
			PHOTOS.put(type, url);
		}
		
		public String getType() {
			return type;
		}

		public String getUrl() {
			return url;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		@Override
		public String toString() {
			return type;
		}
	}


}
