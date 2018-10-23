package com.suleyman.vkclient.api.http.callable;

import com.suleyman.vkclient.api.http.*;

import java.util.concurrent.Callable;

public class GetDocumentUploadServerCallable implements Callable<String> {
	
	private String type;
	private long peerId;
	
	private VKRequest request;

	public GetDocumentUploadServerCallable(String type, long peerId) {
		this.type = type;
		this.peerId = peerId;
		
		request = VKRequest.set("docs.getMessagesUploadServer", "type="+type, "peer_id="+peerId);
	}
	
	@Override
	public String call() throws Exception {
		return VKRestClient.get(request, null);
	}

}
