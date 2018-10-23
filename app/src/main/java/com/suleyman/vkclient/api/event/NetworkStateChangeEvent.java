package com.suleyman.vkclient.api.event;

public class NetworkStateChangeEvent {
	
	public static enum State {
		CONNECTED,

		DISCONNECTED
	}
	
	private State state;

	public NetworkStateChangeEvent(State state) {
		this.state = state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

}
