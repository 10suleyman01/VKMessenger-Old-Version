package com.suleyman.vkclient.api.object.conversations;

import com.google.gson.annotations.*;

import com.suleyman.vkclient.api.http.callable.GetUserCallable;
import com.suleyman.vkclient.api.object.conversations.attachment.ConversationAttachment;
import com.suleyman.vkclient.api.object.user.ObjectUser;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

public class ConversationLastMessage {

	@SerializedName("date")
	@Expose
	private long date;

	@SerializedName("from_id")
	@Expose
	private long fromId;

	@SerializedName("id")
	@Expose
	private long id;

	@SerializedName("out")
	@Expose
	private int out;

	@SerializedName("peer_id")
	@Expose
	private long peerId;

	@SerializedName("text")
	@Expose
	private String text;

	@SerializedName("conversation_message_id")
	@Expose
	private long conversationMessageId;

	@SerializedName("fwd_messages")
	@Expose
	private ArrayList<ForwardConversation> fwdConversation;

	@SerializedName("important")
	@Expose
	private boolean isImportant;

	@SerializedName("random_id")
	@Expose
	private long randomId;

	@SerializedName("attachments")
	@Expose
	private ArrayList<ConversationAttachment> attachments;

	@SerializedName("is_hidden")
	@Expose
	private boolean isHidden;

	@SerializedName("action")
	@Expose
	private Action action;

	private String bodyPhoto;

	private boolean isAttachment;

	public void setBodyPhoto(String bodyPhoto) {
		this.bodyPhoto = bodyPhoto;
	}

	public String getBodyPhoto() {
		return bodyPhoto;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public boolean hasPhotos() {
		for (ConversationAttachment attachment : getAttachments()) {
			if (attachment.getType().equals("photo")) {
				return true;
			}
		}
		return false;
	}

	public void setBodyPhoto(long userId) {
		if (!isOut()) {
			Observable.fromCallable(new GetUserCallable(userId)).
				subscribeOn(Schedulers.io()).
				observeOn(AndroidSchedulers.mainThread()).
				subscribe(new Consumer<ObjectUser>(){
					@Override
					public void accept(ObjectUser user) throws Exception {
						setBodyPhoto(user.getResponse().get(0).getPhoto());
					}
				});
		}
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public void setIsAttachment(boolean isAttachment) {
		this.isAttachment = isAttachment;
	}

	public boolean isAttachment() {
		return isAttachment;
	}

	public void setFwdConversation(ArrayList<ForwardConversation> fwdConversation) {
		this.fwdConversation = fwdConversation;
	}

	public ArrayList<ForwardConversation> getFwdConversations() {
		return fwdConversation;
	}

	public void setIsImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	public boolean isImportant() {
		return isImportant;
	}

	public void setRandomId(long randomId) {
		this.randomId = randomId;
	}

	public long getRandomId() {
		return randomId;
	}

	public void setAttachments(ArrayList<ConversationAttachment> attachments) {
		this.attachments = attachments;
	}

	public ArrayList<ConversationAttachment> getAttachments() {
		return attachments;
	}

	public void setIsHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getDate() {
		return date * 1000;
	}

	public void setFromId(long fromId) {
		this.fromId = fromId;
	}

	public long getFromId() {
		return fromId;
	}

	public void setOut(int out) {
		this.out = out;
	}

	public int getOut() {
		return out;
	}

	public boolean isOut() {
		return getOut() == 1 ? true : false;
	}

	public void setPeerId(long peerId) {
		this.peerId = peerId;
	}

	public long getPeerId() {
		return peerId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBody() {
		if (attachments != null && attachments.size() > 0) {
			setIsAttachment(true);
			for (ConversationAttachment attachment : attachments) {
				String type = attachment.getType();
				switch (type) {
					case "photo" : return "Фотография";
					case "wall" : return "Запись на стене";
					case "audio" : return "Аудиозапись";
					case "doc": return "Документ";
					case "video": return "Видеозапись";
					case "sticker": return "Стикер";
					case "link": return "Ссылка";
					case "graffiti": return "Граффити";
					case "audio_message": return "Голосовое сообщение";
					case "gif": return "GIF-картинка";
					default:
						return type;
				}
			}
		}	
		int size = fwdConversation != null ? fwdConversation.size() : 0;
		if (size != 0) {
			setIsAttachment(true);
			if (size == 1) return "Пересланное сообщение";
			else if (size > 1) return size + " пересланных сообщений";
		}
		return text;
	}

	public String getText() {
		return text;
	}

	public class Action {

		@SerializedName("type")
		@Expose
		public String type;

		@SerializedName("member_id")
		@Expose
		public long memberId;

		@SerializedName("text")
		@Expose
		public String text;

	}

	public void setConversationMessageId(long conversationMessageId) {
		this.conversationMessageId = conversationMessageId;
	}

	public long getConversationMessageId() {
		return conversationMessageId;
	}

	@Override
	public String toString() {
		return getBody() + " " + isAttachment;
	}
}
