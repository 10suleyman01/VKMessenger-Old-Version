package com.suleyman.vkclient.api.http.callable;

import java.util.concurrent.Callable;
import com.suleyman.vkclient.api.http.VKRestClient;

public class LongPollServerCallable implements Callable<String> {
	
	private String server;
	private String key;
	private long ts;

	public LongPollServerCallable(String server, String key, long ts) {
		this.server = server;
		this.key = key;
		this.ts = ts;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getServer() {
		return server;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

	public long getTs() {
		return ts;
	}

	@Override
	public String call() throws Exception {	
		String url = "https://"+server+"?act=a_check&key="+key+"&ts="+ts+"&wait=25&mode=2&version=3";
		return VKRestClient.doGet(url);
	}

	
}
