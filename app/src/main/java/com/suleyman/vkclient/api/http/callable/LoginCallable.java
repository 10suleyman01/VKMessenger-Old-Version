package com.suleyman.vkclient.api.http.callable;



import com.google.gson.Gson;
import com.suleyman.vkclient.api.http.VKRestClient;
import com.suleyman.vkclient.api.object.account.VKAccount;
import java.util.concurrent.Callable;

public class LoginCallable implements Callable<VKAccount> {
	
	private String REDIRECT_URI = "https://oauth.vk.com/blank.html";
	private String login, password;

	public LoginCallable(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	@Override
	public VKAccount call() throws Exception {
		String data = VKRestClient.doGet(getUrl(login, password));
		return new Gson().fromJson(data, VKAccount.class);
	}

	private String getUrl(String email, String password) {
		StringBuilder urlBuilder = new StringBuilder("https://oauth.vk.com/token?grant_type=password");
		urlBuilder.append("&client_id=3697615");
		urlBuilder.append("&client_secret=AlVXZFMUqyrnABp8ncuU");
		urlBuilder.append("&username=" + email);
		urlBuilder.append("&password=" + password);
		urlBuilder.append("&redirect_uri=" + REDIRECT_URI);
		urlBuilder.append("&scope=" + getScope());
		urlBuilder.append(VKRestClient.API_VERSION);
		urlBuilder.append("&2fa_supported=1");
		return urlBuilder.toString();
	}
	
	private String getScope() {
		return "notify,friends,photos,audio,video,docs,status,notes,pages,wall,groups,messages,offline,notifications";
	}
	
}
