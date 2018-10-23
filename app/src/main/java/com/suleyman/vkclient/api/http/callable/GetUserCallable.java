package com.suleyman.vkclient.api.http.callable;

import com.suleyman.vkclient.api.object.user.ObjectUser;
import java.util.concurrent.Callable;
import com.suleyman.vkclient.api.http.VKRequest;
import com.suleyman.vkclient.api.http.VKRestClient;

public class GetUserCallable implements Callable<ObjectUser> {
	
	private long userId;
	private VKRequest request;

	public GetUserCallable(long userId) {
		this.userId = userId;
		
		request = VKRequest.set("users.get", "fields=photo_200_orig,online,last_seen");
	}

	@Override
	public ObjectUser call() throws Exception {
		return VKRestClient.get(request, ObjectUser.class);
	}

}
