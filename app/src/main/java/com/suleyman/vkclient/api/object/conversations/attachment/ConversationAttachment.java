package com.suleyman.vkclient.api.object.conversations.attachment;

import com.google.gson.annotations.*;

public class ConversationAttachment {
	
	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("photo")
	@Expose
	private AttachmentPhoto photo;

	@SerializedName("wall")
	@Expose
	private AttachmentWall wall;

	@SerializedName("sticker")
	@Expose
	private AttachmentSticker sticker;

	@SerializedName("doc")
	@Expose
	private AttachmentDocument document;

	@SerializedName("audio_message")
	@Expose
	private AttachmentAudioMessage audioMessage;

	public void setAudioMessage(AttachmentAudioMessage audioMessage) {
		this.audioMessage = audioMessage;
	}

	public AttachmentAudioMessage getAudioMessage() {
		return audioMessage;
	}


	public void setDocument(AttachmentDocument document) {
		this.document = document;
	}

	public AttachmentDocument getDocument() {
		return document;
	}

	public void setSticker(AttachmentSticker sticker) {
		this.sticker = sticker;
	}

	public AttachmentSticker getSticker() {
		return sticker;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setPhoto(AttachmentPhoto photo) {
		this.photo = photo;
	}

	public AttachmentPhoto getPhoto() {
		return photo;
	}

	public void setWall(AttachmentWall wall) {
		this.wall = wall;
	}

	public AttachmentWall getWall() {
		return wall;
	}
}
