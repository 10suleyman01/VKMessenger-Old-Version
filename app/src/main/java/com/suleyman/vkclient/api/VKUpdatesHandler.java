package com.suleyman.vkclient.api;

public class VKUpdatesHandler {
	public static OnUpdate onUpdate;

	public static void setOnUpdate(OnUpdate onUpdate) {
		VKUpdatesHandler.onUpdate = onUpdate;
	}

	public static OnUpdate getOnUpdate() {
		return onUpdate;
	}
	
	public interface OnUpdate {
		void onUpdate(int[] updates)
	}
}
