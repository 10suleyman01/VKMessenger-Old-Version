package com.suleyman.vkclient.api.object.conversations.longpoll;

import com.google.gson.annotations.*;

import com.suleyman.vkclient.api.object.conversations.ConversationLastMessage;
import java.util.ArrayList;

public class ItemHistory {

	@SerializedName("count")
	@Expose
	private int count;

	@SerializedName("items")
	@Expose
	private ArrayList<ConversationLastMessage> items;

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setItems(ArrayList<ConversationLastMessage> items) {
		this.items = items;
	}

	public ArrayList<ConversationLastMessage> getItems() {
		return items;
	}
}
