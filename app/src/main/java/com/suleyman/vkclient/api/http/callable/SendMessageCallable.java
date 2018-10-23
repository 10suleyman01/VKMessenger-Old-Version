package com.suleyman.vkclient.api.http.callable;

import java.util.concurrent.Callable;
import com.suleyman.vkclient.api.http.VKRequest;
import com.suleyman.vkclient.api.http.VKRestClient;
import java.net.URLEncoder;

public class SendMessageCallable implements Callable<String> {
	
	private VKRequest request;

	public SendMessageCallable(long peerId, String message, String attachment) {
		
		request = VKRequest.set("messages.send", "peer_id="+peerId, "message="+URLEncoder.encode(message), "attachment="+attachment);
	}
	
	@Override
	public String call() throws Exception {
		return VKRestClient.get(request, null);
	}
}
