package com.suleyman.vkclient.module.activity.conversation.adapter;

import android.view.*;
import android.widget.*;

import android.content.Context;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.module.base.adapter.BaseAdapter;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import com.suleyman.vkclient.api.object.conversations.ConversationLastMessage;

public class MessageHolder extends BaseAdapter.BaseViewHolder {

	@ViewInject(R.id.messageContainer)
	private FrameLayout messageContainer;
	
	private Context context;
	private ConversationLastMessage message;

	public MessageHolder(View view) {
		super(view);

		x.view().inject(this, view);
	}
	
	public void setup(Context context, ConversationLastMessage message) {
		this.context = context;
		this.message = message;
	}

	public void addMessages() {
		LinearLayout messageView = (LinearLayout) LayoutInflater.from(context).inflate(!message.isOut() ? R.layout.element_text_in : R.layout.element_text_out, null, false);
		TextView messageText = (TextView) messageView.findViewById(R.id.messageText);
		messageText.setText(message.getText());
		messageContainer.addView(messageView);
	}
	
	public void clear() {
		messageContainer.removeAllViews();
	}

}
