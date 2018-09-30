package com.suleyman.vkclient.api.http;

public class VKRequest {
	
	private String methodName;
	private String[] params;

	public VKRequest(String methodName, String[] params) {
		this.methodName = methodName;
		this.params = params;
	}

	public String getMethod() {
		return methodName;
	}

	public String[] getParams() {
		return params;
	}
	
	public static VKRequest set(String method, String... params) {
		return new VKRequest(method, params);
	}
}
