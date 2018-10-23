package com.suleyman.vkclient.api.event;

public class TypingEvent {
	
	private long id;

	public TypingEvent(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
