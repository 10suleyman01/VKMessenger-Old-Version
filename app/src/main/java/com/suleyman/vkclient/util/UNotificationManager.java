package com.suleyman.vkclient.util;

import android.app.*;

import android.content.Context;
import android.support.v7.app.NotificationCompat;
import com.suleyman.vkclient.R;

public class UNotificationManager {

	private static NotificationCompat.Builder nBuilder;
	
	private static Context mContext;

	public static void setup(Context context) {
		mContext = context;
		nBuilder = new NotificationCompat.Builder(context);
	}

	public static void send(int id, String text) {
		if (nBuilder != null) {
			nBuilder.setSmallIcon(R.drawable.ic_launcher);
			nBuilder.setContentTitle("Notification");
			nBuilder.setContentText(text);

			NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(id, nBuilder.build());
		}
	}

}
