package com.suleyman.vkclient.app;

import android.app.Application;
import com.suleyman.vkclient.util.USharedPreferences;
import java.util.Date;
import java.text.SimpleDateFormat;
import android.content.res.Resources;
import com.suleyman.vkclient.util.UNotificationManager;

public class VKApp extends Application {
	
	private static SimpleDateFormat dateFormatHoursMinutes = new SimpleDateFormat("hh:mm");
	private static SimpleDateFormat dateFormatDays = new SimpleDateFormat("d MMM");
	
	private static Resources resources;

	@Override
	public void onCreate() {
		super.onCreate();
		
		USharedPreferences.setup(this);
		UNotificationManager.setup(this);
		
		resources = getResources();
	}
	
	public static int getColor(int resId) {
		return resources.getColor(resId);
	}
	
	public static String getTimeFormat(long date) {
		Date msgDate = new Date(date);
		Date curDate = new Date();

		int msgDay = msgDate.getDay();
		int curDay = curDate.getDay();

		if (msgDay == curDay) {
			return dateFormatHoursMinutes.format(msgDate);
		} else if (msgDay == curDay - 1) {
			return "вчера";
		} else {
			return dateFormatDays.format(msgDate).replace(".", "");
		}
	}
	
}
