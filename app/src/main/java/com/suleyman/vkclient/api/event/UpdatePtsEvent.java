package com.suleyman.vkclient.api.event;

public class UpdatePtsEvent {
	
	private long pts;

	public UpdatePtsEvent(long pts) {
		this.pts = pts;
	}

	public void setPts(long pts) {
		this.pts = pts;
	}

	public long getPts() {
		return pts;
	}
	
}
