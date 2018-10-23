package com.suleyman.vkclient.api.http.callable;

import com.suleyman.vkclient.api.http.*;

import com.suleyman.vkclient.api.object.doc.ObjectDocument;
import java.util.concurrent.Callable;

public class DocumentSaveCallable implements Callable<ObjectDocument> {
	
	private VKRequest request;

	public DocumentSaveCallable(String file, String title) {
		
		request = VKRequest.set("docs.save", "file="+file, "title="+title);
	}

	@Override
	public ObjectDocument call() throws Exception {
		return VKRestClient.get(request, ObjectDocument.class);
	}

}
