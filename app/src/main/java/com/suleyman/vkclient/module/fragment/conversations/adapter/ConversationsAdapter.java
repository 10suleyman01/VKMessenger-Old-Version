package com.suleyman.vkclient.module.fragment.conversations.adapter;

import android.view.*;

import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.object.conversations.ItemConversation;
import com.suleyman.vkclient.module.base.adapter.BaseAdapter;
import java.util.ArrayList;
import com.suleyman.vkclient.app.VKApp;

public class ConversationsAdapter extends BaseAdapter<ItemConversation, ConversationHolder> {
	
	private ArrayList<ItemConversation> conversations;

	public ConversationsAdapter(ArrayList<ItemConversation> conversations) {
		super(conversations);
		this.conversations = conversations;
	}

	@Override
	public ConversationHolder onCreateHolder(ViewGroup parent, int viewType) {
		return new ConversationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false));
	}

	@Override
	public void onBind(ConversationHolder holder, int pos) {
		
		ItemConversation conversation = conversations.get(pos);
		
		holder.setTitle(conversation.getTitle());
		holder.setText(conversation.getLastMessage().getText(), conversation.getLastMessage().isAttachment());
		holder.setOnline(conversation.isOnline());
		holder.setPhoto(conversation.getPhoto());
		holder.setDate(VKApp.getTimeFormat(conversation.getLastMessage().getDate()));
		holder.setReadState(conversation.getLastMessage().isOut(), conversation.getConversation().isOutMessageRead(), conversation.getConversation().getUnreadCount());
		
	}

}
