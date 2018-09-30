package com.suleyman.vkclient.api.object.conversations.longpoll;

import com.google.gson.annotations.*;
import com.suleyman.vkclient.api.object.conversations.*;

import java.util.ArrayList;

public class ResponseLongPollHistory {
	
	@SerializedName("history")
	@Expose
	private int[][] history;

	@SerializedName("messages")
	@Expose
	private ItemHistory messages;

	@SerializedName("new_pts")
	@Expose
	private long newPts;

	@SerializedName("profiles")
	@Expose
	private ArrayList<ProfileConversation> profiles;

	@SerializedName("conversations")
	@Expose
	private ArrayList<SubItemConversation> conversations;

	public void setMessages(ItemHistory messages) {
		this.messages = messages;
	}

	public ItemHistory getMessages() {
		return messages;
	}

	public void setHistory(int[][] history) {
		this.history = history;
	}

	public int[][] getHistory() {
		return history;
	}

	public void setNewPts(long newPts) {
		this.newPts = newPts;
	}

	public long getNewPts() {
		return newPts;
	}

	public void setProfiles(ArrayList<ProfileConversation> profiles) {
		this.profiles = profiles;
	}

	public ArrayList<ProfileConversation> getProfiles() {
		return profiles;
	}

	public void setConversations(ArrayList<SubItemConversation> conversations) {
		this.conversations = conversations;
	}

	public ArrayList<SubItemConversation> getConversations() {
		return conversations;
	}
	
}
