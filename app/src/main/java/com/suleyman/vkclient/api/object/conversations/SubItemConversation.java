package com.suleyman.vkclient.api.object.conversations;

import com.google.gson.annotations.*;

import android.os.Parcelable;
import android.os.Parcel;

public class SubItemConversation {
	
	@SerializedName("peer")
	@Expose
	private Peer peer;

	@SerializedName("in_read")
	@Expose
	private long inRead;

	@SerializedName("out_read")
	@Expose
	private long outRead;

	@SerializedName("unread_count")
	@Expose
	private int unreadCount;

	@SerializedName("last_message_id")
	@Expose
	private long lastMessageId;

	@SerializedName("can_write")
	@Expose
	private CanWrite canWrite;

	@SerializedName("chat_settings")
	@Expose
	private ChatSettings chatSettings;
	
	private boolean isChat;
	
	public void setInRead(long inRead) {
		this.inRead = inRead;
	}

	public long getInRead() {
		return inRead;
	}

	public void setOutRead(long outRead) {
		this.outRead = outRead;
	}

	public long getOutRead() {
		return outRead;
	}
	
	public boolean isOutMessageRead() {
		return outRead == lastMessageId;
	}
	
	public boolean isOutMessageRead(long lastMessageId) {
		return outRead == lastMessageId;
	}
	
	public boolean isInMessageRead() {
		return inRead == lastMessageId;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	public int getUnreadCount() {
		return unreadCount;
	}
	
	public boolean isChat() {
		isChat = chatSettings != null;
		return isChat;
	}

	public void setLastMessageId(long lastMessageId) {
		this.lastMessageId = lastMessageId;
	}

	public long getLastMessageId() {
		return lastMessageId;
	}

	public void setCanWrite(CanWrite canWrite) {
		this.canWrite = canWrite;
	}

	public CanWrite getCanWrite() {
		return canWrite;
	}

	public void setChatSettings(ChatSettings chatSettings) {
		this.chatSettings = chatSettings;
	}

	public ChatSettings getChatSettings() {
		return chatSettings;
	}

	public void setPeer(Peer peer) {
		this.peer = peer;
	}

	public Peer getPeer() {
		return peer;
	}

	public static class Peer {	
		@SerializedName("id")
		@Expose
		public long id;

		@SerializedName("type")
		@Expose
		public String type;

		@SerializedName("local_id")
		@Expose
		public int local_id;	
	}

	public static class CanWrite {
		@SerializedName("allowed")
		@Expose
		public boolean isAllowed;
	}
}
