package com.suleyman.vkclient.api.object.conversationHistory;

import com.google.gson.annotations.*;
import com.suleyman.vkclient.api.object.conversations.*;

import java.util.ArrayList;

public class ResponseConversationHistory {
	
	@SerializedName("count")
	@Expose
	private int count;

	@SerializedName("conversations")
	@Expose
	private ArrayList<SubItemConversation> conversation;

	@SerializedName("items")
	@Expose
	private ArrayList<ConversationLastMessage> lastMessage;

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setConversation(ArrayList<SubItemConversation> conversation) {
		this.conversation = conversation;
	}

	public ArrayList<SubItemConversation> getConversation() {
		return conversation;
	}

	public void setLastMessage(ArrayList<ConversationLastMessage> lastMessage) {
		this.lastMessage = lastMessage;
	}

	public ArrayList<ConversationLastMessage> getLastMessage() {
		return lastMessage;
	}
}
