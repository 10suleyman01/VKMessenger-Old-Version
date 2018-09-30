package com.suleyman.vkclient.api.http;

import okhttp3.*;

import android.util.Log;
import com.google.gson.Gson;
import com.suleyman.vkclient.api.object.account.VKAccountManager;
import java.io.IOException;

public class VKRestClient {

	public static String API_HOST = "https://api.vk.com/method/";
	public static String API_VERSION = "&v=5.85";

	private static OkHttpClient httpClient;

	static {
		httpClient = new OkHttpClient.Builder().
			build();
	}

	public static <T> T get(VKRequest method, Class<T> type) throws IOException {
		String data = doGet(vkApiUrl(method));
		return new Gson().fromJson(data, type);
	}

	private static String vkApiUrl(VKRequest request) {
		return API_HOST + request.getMethod() + "?" + getParams(request.getParams()) + getToken() + API_VERSION;
	}

	private static String getToken() {
		return "&access_token=" + VKAccountManager.getToken();
	}

	private static String getParams(String... params) {
		StringBuilder buffer = new StringBuilder();
		for (String param : params) {
			buffer.append(param + "&");
		}
		return buffer.toString().replace(" ", "%20").substring(0, buffer.length() - 1);
	}

	public static String doGet(String url) throws IOException {
		Request request = getRequest(url);
		Response response = httpClient.newCall(request).execute();
		return response.body().string();
	}

	private static Request getRequest(String url) {
		return new Request.Builder().
			url(url).
			build(); 
	}

}
