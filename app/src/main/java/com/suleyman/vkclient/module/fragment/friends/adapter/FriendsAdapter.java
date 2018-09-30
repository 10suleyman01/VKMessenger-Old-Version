package com.suleyman.vkclient.module.fragment.friends.adapter;

import android.view.*;

import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.object.friends.ItemFriend;
import com.suleyman.vkclient.module.base.adapter.BaseAdapter;
import java.util.ArrayList;

public class FriendsAdapter extends BaseAdapter<ItemFriend, FriendHolder> {
	
	private ArrayList<ItemFriend> friends;

	public FriendsAdapter(ArrayList<ItemFriend> friends) {
		super(friends);
		this.friends = friends;
	}

	@Override
	public FriendHolder onCreateHolder(ViewGroup parent, int viewType) {
		return new FriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false));
	}

	@Override
	public void onBind(FriendHolder holder, int pos) {
		ItemFriend friend = friends.get(pos);
		
		holder.setFriendPhoto(friend.getPhoto());
		holder.setFriendOnline(friend.isOnline());
		holder.setFriendTitle(friend.getTitle());
	}

}
