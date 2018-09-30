package com.suleyman.vkclient.api.http.callable;

import com.suleyman.vkclient.api.http.*;

import com.suleyman.vkclient.api.object.conversationHistory.ObjectConversationHistory;
import java.util.concurrent.Callable;

public class GetConversationHistoryCallable implements Callable<ObjectConversationHistory> {

	private VKRequest request;

	public GetConversationHistoryCallable(long peerId, int offset) {
		this.request = VKRequest.set("messages.getHistory", "count=200", "peer_id="+peerId, "offset="+offset, "extended=1");
	}

	@Override
	public ObjectConversationHistory call() throws Exception {
		
		return VKRestClient.get(request, ObjectConversationHistory.class);
	}

}
