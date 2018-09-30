package com.suleyman.vkclient.module.fragment.friends.adapter;

import android.view.View;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.module.base.adapter.BaseAdapter;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import com.bumptech.glide.Glide;
import android.widget.ImageView;

public class FriendHolder extends BaseAdapter.BaseViewHolder {

	@ViewInject(R.id.friendPhoto)
	private CircleImageView friendPhoto;

	@ViewInject(R.id.friendTitle)
	private TextView friendTitle;
	
	@ViewInject(R.id.friendOnline)
	private ImageView friendOnline;

	public FriendHolder(View view) {
		super(view);

		x.view().inject(this, view);
	}

	public void setFriendTitle(String title) {
		friendTitle.setText(title);
	}

	public void setFriendPhoto(String url) {
		if (url != null && url.length() > 0) {
			Glide.with(friendPhoto).
				load(url).
				into(friendPhoto);
		}
	}
	
	public void setFriendOnline(boolean isOnline) {
		if (isOnline) {
			//friendPhoto.setBorderWidth(3);
			//friendPhoto.setBorderColorResource(R.color.online);
			friendOnline.setBackgroundResource(R.drawable.online_shape);
		} else {
			//friendPhoto.setBorderWidth(0);
			friendOnline.setVisibility(View.GONE);
		}
	}
	
}
