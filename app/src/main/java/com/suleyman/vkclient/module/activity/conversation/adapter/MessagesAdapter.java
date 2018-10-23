package com.suleyman.vkclient.module.activity.conversation.adapter;

import android.view.*;

import android.support.v7.app.AppCompatActivity;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.object.conversations.ConversationLastMessage;
import com.suleyman.vkclient.module.activity.conversation.adapter.MessageHolder;
import com.suleyman.vkclient.module.base.adapter.BaseAdapter;
import java.util.ArrayList;

public class MessagesAdapter extends BaseAdapter<ConversationLastMessage, MessageHolder> {

	private static final int MESSAGE_OUT = 2;
	private static final int MESSAGE_IN = 4;

	public AppCompatActivity activity;
	public ArrayList<ConversationLastMessage> messages;

	public MessagesAdapter(AppCompatActivity activity, ArrayList<ConversationLastMessage> messages) {
		super(messages);
		this.messages = messages;
		this.activity = activity;
	}

	@Override
	public MessageHolder onCreateHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case MESSAGE_OUT: return new MessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_out, parent, false));
			case MESSAGE_IN: return new MessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_in, parent, false));
		}
		return null;
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

	public void addMessage(ConversationLastMessage message) {
		messages.add(0, message);
		notifyItemInserted(0);
	}

	public void deleteMessage(long id) {
		for (int i = 0; i < messages.size();  i++) {
			ConversationLastMessage message = messages.get(i);
			if (message.getId() == id) {
				messages.remove(i);
				notifyItemRemoved(i);
			}
		}
	}

	@Override
	public void onBind(MessageHolder holder, int pos) {
		ConversationLastMessage message = messages.get(pos);
		holder.clear();
		holder.setup(activity, message, holder.itemView.getContext());
		holder.addMessages();
		holder.addAttachments();
		holder.addForwards();
	}

}
