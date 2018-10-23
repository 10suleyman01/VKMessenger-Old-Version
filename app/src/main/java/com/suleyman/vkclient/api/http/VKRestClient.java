package com.suleyman.vkclient.api.http;

import java.io.*;
import okhttp3.*;

import com.google.gson.Gson;
import com.suleyman.vkclient.api.object.account.VKAccountManager;

public class VKRestClient {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static String API_HOST = "https://api.vk.com/method/";
	public static String API_VERSION = "&v=5.87";

	private static OkHttpClient httpClient;

	static {
		httpClient = new OkHttpClient.Builder().build();
	}

	public static <T> T get(VKRequest request, Class<T> type) throws IOException {
		String data = doGet(vkApiUrl(request));
		if (type != null) return new Gson().fromJson(data, type);
		return (T) data;
	}

	public static void uploadFile(String uploadUrl, File file, final OnUploadCallback onUploadCallback) throws IOException {	
		RequestBody body = new MultipartBody.Builder()
		.setType(MultipartBody.FORM)
		.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("text/plain"),file))
		.build();
		Request request = postRequest(uploadUrl, body);
		httpClient.newCall(request).enqueue(new Callback(){
				@Override
				public void onFailure(Call call, IOException error) {
					onUploadCallback.onError(error);
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					onUploadCallback.onUpload(response);
				}
		});
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

	public static String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Response response = httpClient.newCall(postRequest(url, body)).execute(); 
		return response.body().string(); 
	}

	private static Request getRequest(String url) {
		return new Request.Builder().
			url(url).
			build(); 
	}

	private static Request postRequest(String url, RequestBody body) {
		return new Request.Builder().
			url(url).
			post(body).
			build(); 
	}

	public interface OnUploadCallback {
		void onUpload(Response response)
		void onError(IOException error);
	}
	
}
