package com.suleyman.vkclient.util;

import org.greenrobot.eventbus.EventBus;

public class UEventBus {

	public static void register(Object subscriber) {
		if (EventBus.getDefault().isRegistered(subscriber)) return;
		EventBus.getDefault().register(subscriber);
	}

	public static void unregister(Object subscriber) {
		if (!EventBus.getDefault().isRegistered(subscriber)) return;
		EventBus.getDefault().unregister(subscriber);
	}

	public static void post(Object event) {
		EventBus.getDefault().post(event);
	}
}
