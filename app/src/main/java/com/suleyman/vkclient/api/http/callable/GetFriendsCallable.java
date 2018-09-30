package com.suleyman.vkclient.api.http.callable;

import com.suleyman.vkclient.api.http.*;

import com.suleyman.vkclient.api.object.friends.ObjectFriends;
import java.util.concurrent.Callable;

public class GetFriendsCallable implements Callable<ObjectFriends> {
	
	private VKRequest request;

	public GetFriendsCallable() {
		this.request = VKRequest.set("friends.get", "fields=photo_200_orig,online");
	}

	@Override
	public ObjectFriends call() throws Exception {
		return VKRestClient.get(request, ObjectFriends.class);
	}

}
