package com.suleyman.vkclient.api.object.longPollServer;

import com.google.gson.annotations.*;

public class ResponseLongPoll {
	@SerializedName("key")
	@Expose
	private String key;

	@SerializedName("server")
	@Expose
	private String server;

	@SerializedName("ts")
	@Expose
	private long ts;

	@SerializedName("pts")
	@Expose
	private long pts;

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getServer() {
		return server;
	}

	public void update(long ts) {
		this.ts = ts;
	}

	public long getTs() {
		return ts;
	}

	public void setPts(long pts) {
		this.pts = pts;
	}

	public long getPts() {
		return pts;
	}

	@Override
	public String toString() {
		return String.format("Server:%s\nKey:%s\nTs:%s", server, key, ts);
	}
}
