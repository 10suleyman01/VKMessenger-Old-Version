package com.suleyman.vkclient.util;

import java.util.*;

import io.reactivex.disposables.Disposable;

public class DisposableManager {
	
	private static Map<String, Disposable> mapDisposables;
	
	static {
		mapDisposables = new HashMap<>();
	}
	
	public static void add(String key, Disposable disposable) {
		mapDisposables.put(key, disposable);
	}

	public static void dispose(String key) {
		if (mapDisposables.containsKey(key)) {
			mapDisposables.get(key).dispose();
		}
	}
	
	private DisposableManager() {}
}
