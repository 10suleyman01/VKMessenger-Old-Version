package com.suleyman.vkclient.api.http.callable;

import com.suleyman.vkclient.api.http.*;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class DeleteMessageCallable implements Callable<String> {

	private VKRequest request;

	public DeleteMessageCallable(ArrayList<Long> messageIds, boolean spam, long groupId, boolean deleteForAll) {

		StringBuilder buffer = new StringBuilder();
		for (Long id : messageIds) {
			buffer.append(id.longValue() + ",");
		}

		request = VKRequest.set("messages.delete", "message_ids=" + buffer.toString(),
								"spam=" + (spam ? 1 : 0),
								groupId != 0 ? "group_id="+groupId : "",
								"delete_for_all=" + (deleteForAll ? "1" : "0"));
	}

	@Override
	public String call() throws Exception {
		return VKRestClient.get(request, null);
	}

}
