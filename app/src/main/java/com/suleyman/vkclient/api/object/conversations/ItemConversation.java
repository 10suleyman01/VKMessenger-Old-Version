package com.suleyman.vkclient.api.object.conversations;

import com.google.gson.annotations.*;

public class ItemConversation {

	@SerializedName("conversation")
	@Expose
	private SubItemConversation conversation;

	@SerializedName("last_message")
	@Expose
	private ConversationLastMessage lastMessage;

	private String photo;

	private String bodyPhoto;

	private String title;

	private boolean online;

	public void setBodyPhoto(String bodyPhoto) {
		this.bodyPhoto = bodyPhoto;
	}

	public String getBodyPhoto() {
		return bodyPhoto;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isOnline() {
		return online;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
	public boolean isChat() {
		return conversation.getPeer().type.equals("chat");
	}
	
	public boolean isGroup() {
		return conversation.getPeer().type.equals("group");
	}
	
	public void setConversation(SubItemConversation conversation) {
		this.conversation = conversation;
	}

	public SubItemConversation getConversation() {
		return conversation;
	}

	public void setLastMessage(ConversationLastMessage lastMessage) {
		this.lastMessage = lastMessage;
	}

	public ConversationLastMessage getLastMessage() {
		return lastMessage;
	}

	@Override
	public String toString() {
		return getTitle() + " " + getLastMessage();
	}
}
