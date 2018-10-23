package com.suleyman.vkclient.module.fragment.conversations.adapter;

import android.view.*;

import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.object.conversations.ItemConversation;
import com.suleyman.vkclient.app.VKApp;
import com.suleyman.vkclient.module.base.adapter.BaseAdapter;
import java.util.ArrayList;

public class ConversationsAdapter extends BaseAdapter<ItemConversation, ConversationHolder> {

	private ArrayList<ItemConversation> items;

	public ConversationsAdapter(ArrayList<ItemConversation> conversations) {
		super(conversations);
		this.items = conversations;
	}

	@Override
	public ConversationHolder onCreateHolder(ViewGroup parent, int viewType) {
		return new ConversationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false));
	}

	@Override
	public void onBind(ConversationHolder holder, int pos) {

		ItemConversation conversation = items.get(pos);

		holder.setTitle(conversation.getTitle());
		holder.setBodyPhoto(conversation.getBodyPhoto(), conversation.getLastMessage().isOut() || conversation.getConversation().isChat());
		holder.setText(conversation.getLastMessage().getBody(), conversation.getLastMessage().isAttachment());
		holder.setOnline(conversation.isOnline());
		holder.setPhoto(conversation.getPhoto(), conversation.isChat() && conversation.getConversation().getChatSettings().getState().equals("left"));
		holder.setDate(VKApp.getTimeFormat(conversation.getLastMessage().getDate()));

		holder.setReadState(conversation.getLastMessage().isOut(), 
							conversation.getConversation().isOutMessageRead(),
							conversation.getConversation().getUnreadCount(), 
							conversation.isChat() || conversation.isGroup()
							);

	}
	
	public void setConversation(long peerId, ArrayList<ItemConversation> items, ItemConversation newItem) {
		for (int i = 0; i < items.size(); i++) {
			ItemConversation item = items.get(i);
			if (item.getLastMessage().getPeerId() == peerId) {
				if (newItem != null) {
					item = newItem;
				} 
				items.set(i, item);
				notifyItemChanged(i, item);
			}
		}
	}
}
