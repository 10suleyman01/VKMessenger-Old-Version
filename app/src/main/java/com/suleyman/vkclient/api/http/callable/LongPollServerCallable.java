package com.suleyman.vkclient.api.http.callable;

import java.util.concurrent.Callable;
import com.suleyman.vkclient.api.http.VKRestClient;
import android.util.Log;

public class LongPollServerCallable implements Callable<String> {
	
	private String server;
	private String key;
	private long ts;
	
	private String url;

	public LongPollServerCallable(String server, String key, long ts) {
		this.server = server;
		this.key = key;
		this.ts = ts;
		
		url = getServerUrl(server, key, ts);
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
	
	public void update(long ts) {
		url = getServerUrl(server, key, ts);
	}

	public long getTs() {
		return ts;
	}
	
	public String getServerUrl(String server, String key, long ts){
		return "https://"+server+"?act=a_check&key="+key+"&ts="+ts+"&wait=25&mode=2&version=3";
	}

	@Override
	public String call() throws Exception {
		Log.d(this.getClass().getSimpleName(), url);
		return VKRestClient.doGet(url);
	}
}
