package com.suleyman.vkclient.api.object.account;

import com.suleyman.vkclient.util.USharedPreferences;

public class VKAccountManager {
	
	public static final String KEY_TOKEN = "access_token";
	public static final String KEY_USER_ID = "user_id";

	public static void save(VKAccount account) {	
		USharedPreferences.put(KEY_TOKEN, account.getToken());
		USharedPreferences.put(KEY_USER_ID, account.getUserId());	
	}

	public static String getToken(){
		return USharedPreferences.getString(KEY_TOKEN);
	}

	public static long getUserId() {
		return USharedPreferences.getLong(KEY_USER_ID);
	}

	public static void logout() {
		USharedPreferences.remove(KEY_TOKEN, KEY_USER_ID);
	}

	public static boolean isLogined() {
		return USharedPreferences.getString(KEY_TOKEN) != null;
	}
}
