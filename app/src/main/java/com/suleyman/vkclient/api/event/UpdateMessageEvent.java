package com.suleyman.vkclient.api.event;

import com.suleyman.vkclient.api.object.conversations.*;

public class UpdateMessageEvent {
	
	private SubItemConversation conversation;
	private ConversationLastMessage lastMessage;

	public UpdateMessageEvent(SubItemConversation conversation, ConversationLastMessage lastMessage) {
		this.conversation = conversation;
		this.lastMessage = lastMessage;
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
}
