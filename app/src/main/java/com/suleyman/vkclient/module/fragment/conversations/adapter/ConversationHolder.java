package com.suleyman.vkclient.module.fragment.conversations.adapter;

import android.widget.*;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.app.VKApp;
import com.suleyman.vkclient.module.base.adapter.BaseAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class ConversationHolder extends BaseAdapter.BaseViewHolder {

	@ViewInject(R.id.conversationTitle)
	private TextView conversationTitle;

	@ViewInject(R.id.conversationPhoto)
	private CircleImageView conversationPhoto;

	@ViewInject(R.id.conversationBodyPhoto)
	private CircleImageView conversationBodyPhoto;

	@ViewInject(R.id.conversationText)
	private TextView conversationText;

	@ViewInject(R.id.conversationReadState)
	private ImageView readState;

	@ViewInject(R.id.conversationUnreadCount)
	private TextView unreadCountView;

	@ViewInject(R.id.conversationDate)
	private TextView conversationDate;

	public ConversationHolder(View view) {
		super(view);

		x.view().inject(this, view);
	}

	public void setTitle(String title) {
		conversationTitle.setText(title);
	}

	public void setText(String text, boolean isAttachment) {
		if (isAttachment) {
			conversationText.setTextColor(VKApp.getColor(R.color.primary));
		} else {
			conversationText.setTextColor(VKApp.getColor(R.color.secondary_text));
		}
		conversationText.setText(text);
	}

	public void setReadState(boolean isOut, boolean isRead, int unreadCount, boolean isChatOrGroup) {
		if (isOut && !isRead) {
			readState.setBackgroundResource(R.drawable.small_circle_shape);
		} else if (!isOut && unreadCount > 0) {

			readState.setVisibility(View.GONE);

			unreadCountView.setTextColor(Color.WHITE);
			unreadCountView.setText(Integer.toString(unreadCount));
			unreadCountView.setBackgroundResource(isChatOrGroup ? R.drawable.circle_shape_primary : R.drawable.circle_shape);

		} else {
			readState.setVisibility(View.GONE);
			unreadCountView.setVisibility(View.GONE);
		}
	}

	public void setDate(String date) {
		conversationDate.setText(date);
	}

	public void setPhoto(String url, boolean isChatAndStateIn) {
		if (!TextUtils.isEmpty(url)) {
			Glide.with(conversationPhoto).
				load(url).
				apply(new RequestOptions().
					  placeholder(new ColorDrawable(VKApp.getColor(R.color.primary)))).
				into(conversationPhoto);
		} else {
			if (isChatAndStateIn) {
				Glide.with(conversationPhoto).
					load(R.drawable.multichat_icon).
					apply(new RequestOptions().
						  placeholder(new ColorDrawable(VKApp.getColor(R.color.primary)))).
					into(conversationPhoto);
			}
		}
	}

	public void setBodyPhoto(String url, boolean isOutOrChat) {
		if (isOutOrChat) {
			conversationBodyPhoto.setVisibility(View.VISIBLE);
			Glide.with(conversationBodyPhoto).
				load(url).
				apply(new RequestOptions().
					  placeholder(new ColorDrawable(VKApp.getColor(R.color.primary)))).
				into(conversationBodyPhoto);
		} else {
			conversationBodyPhoto.setVisibility(View.GONE);
		}
	}

	public void setOnline(boolean isOnline) {
		if (isOnline) {
			conversationPhoto.setBorderWidth(3);
			conversationPhoto.setBorderColorResource(R.color.online);
		} else {
			conversationPhoto.setBorderWidth(0);
		}
	}
}
