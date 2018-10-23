package com.suleyman.vkclient.api.http.callable;

import com.suleyman.vkclient.api.http.*;

import com.suleyman.vkclient.api.object.longPollServer.ObjectLongPollServer;
import java.util.concurrent.Callable;

public class GetLongPollObjectCallable implements Callable<ObjectLongPollServer> {
	
	private VKRequest request;

	public GetLongPollObjectCallable() {
		this.request = VKRequest.set("messages.getLongPollServer", "need_pts=1", "lp_version=3");
	}

	@Override
	public ObjectLongPollServer call() throws Exception {
		return VKRestClient.get(request, ObjectLongPollServer.class);
	}

}
