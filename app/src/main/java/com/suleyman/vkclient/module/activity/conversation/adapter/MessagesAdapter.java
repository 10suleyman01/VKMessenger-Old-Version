package com.suleyman.vkclient.module.activity.conversation.adapter;

import android.view.*;

import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.object.conversations.ConversationLastMessage;
import com.suleyman.vkclient.module.activity.conversation.adapter.MessageHolder;
import com.suleyman.vkclient.module.base.adapter.BaseAdapter;
import java.util.ArrayList;

public class MessagesAdapter extends BaseAdapter<ConversationLastMessage, MessageHolder> {
	
	private ArrayList<ConversationLastMessage> messages;

	private static final int MESSAGE_OUT = 1;
	private static final int MESSAGE_IN = 2;

	public MessagesAdapter(ArrayList<ConversationLastMessage> messages) {
		super(messages);
		this.messages = messages;
	}

	@Override
	public MessageHolder onCreateHolder(ViewGroup parent, int viewType) {
		View view = null;
		if (viewType == MESSAGE_OUT) {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_out, parent, false);
		} else {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_in, parent, false);
		}
		return new MessageHolder(view);
	}

	@Override
	public int getItemViewType(int position) {
		ConversationLastMessage message = messages.get(position);
		if (message.isOut()) {
			return MESSAGE_OUT;
		} else {
			return MESSAGE_IN;
		}
	}

	@Override
	public void onBind(MessageHolder holder, int pos) {
		ConversationLastMessage message = messages.get(pos);
		
		holder.setup(holder.itemView.getContext(), message);
		
		holder.clear();
		
		holder.addMessages();
	}

}
