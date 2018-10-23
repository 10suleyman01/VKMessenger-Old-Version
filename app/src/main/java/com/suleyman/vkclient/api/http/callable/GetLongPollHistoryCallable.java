package com.suleyman.vkclient.api.http.callable;

import com.suleyman.vkclient.api.object.conversations.longpoll.ObjectLongPollHistory;
import java.util.concurrent.Callable;
import com.suleyman.vkclient.api.http.VKRequest;
import com.suleyman.vkclient.api.http.VKRestClient;
import android.util.Log;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import com.suleyman.vkclient.api.event.UpdatePtsEvent;
import com.suleyman.vkclient.util.UEventBus;

public class GetLongPollHistoryCallable implements Callable<ObjectLongPollHistory> {

	private long ts;
	private long pts;

	private VKRequest request;

	public GetLongPollHistoryCallable(long ts, long pts) {

		UEventBus.register(this);

		this.ts = ts;
		this.pts = pts;

		this.request = VKRequest.set("messages.getLongPollHistory", "ts=" + ts, "pts=" + pts, "events_limit=1000", "msgs_limit=200", "lp_version=3");
	}

	public void updatePts(long pts) {
		request = VKRequest.set("messages.getLongPollHistory", "ts=" + ts, "pts=" + pts, "events_limit=1000", "msgs_limit=200", "lp_version=3");
	}

	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	public void onUpdatePts(UpdatePtsEvent event) {
		updatePts(event.getPts());
	}

	public long getTs() {
		return ts;
	}

	public long getPts() {
		return pts;
	}

	public void unregisterBus() {
		UEventBus.unregister(this);
	}

	@Override
	public ObjectLongPollHistory call() throws Exception {
		return VKRestClient.get(request, ObjectLongPollHistory.class);
	}

}
