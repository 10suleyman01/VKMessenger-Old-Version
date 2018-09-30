package com.suleyman.vkclient.util;

import android.content.*;

public class USharedPreferences {

	private static String PREF_NAME = "s_app_setting";

	private static SharedPreferences pref;
	private static SharedPreferences.Editor editor;

	public static void setup(Context context) {
		pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		editor = pref.edit();
	}

	public static void put(String key, String value) {
		editor.putString(key, value);
		editor.apply();
	}

	public static void put(String key, int value) {
		editor.putInt(key, value);
		editor.apply();
	}

	public static void put(String key, long value) {
		editor.putLong(key, value);
		editor.apply();
	}

	public static void put(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.apply();
	}

	public static String getString(String key) {
		return pref.getString(key, null);
	}

	public static int getInt(String key) {
		return pref.getInt(key, 0);
	}

	public static long getLong(String key) {
		return pref.getLong(key, 0);
	}

	public static boolean getBoolean(String key) {
		return pref.getBoolean(key, false);
	}

	public static void remove(String... keys) {
		for (String key : keys) {
			editor.remove(key);
			editor.apply();
		}
	}
}
