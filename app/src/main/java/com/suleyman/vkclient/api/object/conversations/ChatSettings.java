package com.suleyman.vkclient.api.object.conversations;

import com.google.gson.annotations.*;

public class ChatSettings {
	
	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("members_count")
	@Expose
	private String countMembers;

	@SerializedName("state")
	@Expose
	private String state;

	@SerializedName("photo")
	@Expose
	private Photo photo;

	@SerializedName("active_ids")
	@Expose
	private long[] activeIds;
	
	@SerializedName("is_group_channel")
	@Expose
	private boolean isGroupChannel;

	public void setIsGroupChannel(boolean isGroupChannel) {
		this.isGroupChannel = isGroupChannel;
	}

	public boolean isGroupChannel() {
		return isGroupChannel;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setCountMembers(String countMembers) {
		this.countMembers = countMembers;
	}

	public String getCountMembers() {
		return countMembers;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setActiveIds(long[] activeIds) {
		this.activeIds = activeIds;
	}

	public long[] getActiveIds() {
		return activeIds;
	}

	public static class Photo {

		@SerializedName("photo_50")
		@Expose
		private String photo50;

		@SerializedName("photo_100")
		@Expose
		private String photo100;

		@SerializedName("photo_200")
		@Expose
		private String photo200;

		public String getPhoto50() {
			return photo50;
		}

		public String getPhoto100() {
			return photo100;
		}

		public String getPhoto200() {
			return photo200;
		}
	}
}
