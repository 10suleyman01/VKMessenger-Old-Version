package com.suleyman.vkclient.api.object.conversations;

import com.google.gson.annotations.*;

import java.util.ArrayList;

public class ResponseConversations {
	
	@SerializedName("count")
	@Expose
	private int count;

	@SerializedName("items")
	@Expose
	private ArrayList<ItemConversation> items;

	@SerializedName("unread_count")
	@Expose
	private int unreadMessages;

	@SerializedName("profiles")
	@Expose
	private ArrayList<ProfileConversation> profiles;

	@SerializedName("groups")
	@Expose
	private ArrayList<GroupConversation> groups;

	public void setUnreadMessages(int unreadMessages) {
		this.unreadMessages = unreadMessages;
	}

	public int getUnreadMessages() {
		return unreadMessages;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setItems(ArrayList<ItemConversation> items) {
		this.items = items;
	}

	public ArrayList<ItemConversation> getItems() {
		return items;
	}

	public void setProfiles(ArrayList<ProfileConversation> profiles) {
		this.profiles = profiles;
	}

	public ArrayList<ProfileConversation> getProfiles() {
		return profiles;
	}

	public void setGroups(ArrayList<GroupConversation> groups) {
		this.groups = groups;
	}

	public ArrayList<GroupConversation> getGroups() {
		return groups;
	}

	@Override
	public String toString() {
		return String.format("%s\n%s", items, profiles);
	}
}
