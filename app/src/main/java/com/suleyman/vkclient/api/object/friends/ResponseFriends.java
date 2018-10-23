package com.suleyman.vkclient.api.object.friends;

import com.google.gson.annotations.*;
import java.util.*;

import com.suleyman.vkclient.api.object.friends.ItemFriend;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ResponseFriends {

	@SerializedName("count")
	@Expose
	private long count;
	
	@SerializedName("items")
	@Expose
	private ArrayList<ItemFriend> items;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public ArrayList<ItemFriend> getItems() {
		Collections.sort(items, new Comparator<ItemFriend>(){
				@Override
				public int compare(ItemFriend item1, ItemFriend item2) {
					return item1.getTitle().compareTo(item2.getTitle());
				}
			});
		return items;
	}

	public void setItems(ArrayList<ItemFriend> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return items.toString();
	}
}
