package com.suleyman.vkclient.api.object.conversations;

import com.google.gson.annotations.*;

import com.suleyman.vkclient.api.object.conversations.attachment.ConversationAttachment;
import java.util.ArrayList;

public class ForwardConversation {

	@SerializedName("date")
	@Expose
	private long date;

	@SerializedName("from_id")
	@Expose
	private long fromId;

	@SerializedName("text")
	@Expose
	private String text;

	@SerializedName("fwd_messages")
	@Expose
	private ArrayList<ForwardConversation> fwdMessages;

	@SerializedName("attachments")
	@Expose
	private ArrayList<ConversationAttachment> attachments;

	@SerializedName("update_time")
	@Expose
	private long updateTime;

	public void setFwdMessages(ArrayList<ForwardConversation> fwdMessages) {
		this.fwdMessages = fwdMessages;
	}

	public ArrayList<ForwardConversation> getFwdConversations() {
		return fwdMessages;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getDate() {
		return date;
	}

	public void setFromId(long fromId) {
		this.fromId = fromId;
	}

	public long getFromId() {
		return fromId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setAttachments(ArrayList<ConversationAttachment> attachments) {
		this.attachments = attachments;
	}

	public ArrayList<ConversationAttachment> getAttachments() {
		return attachments;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	@Override
	public String toString() {
		return getText();
	}
}
