package com.suleyman.vkclient.app;

import android.app.*;
import android.util.*;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import com.suleyman.vkclient.util.USharedPreferences;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VKApp extends Application {

	private static SimpleDateFormat dateFormatHoursMinutes = new SimpleDateFormat("hh:mm");
	private static SimpleDateFormat dateFormatDays = new SimpleDateFormat("d MMM");

	private static Resources resources;

	@Override
	public void onCreate() {
		super.onCreate();

		USharedPreferences.setup(this);
		resources = getResources();
		
		RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
				@Override
				public void accept(Throwable error) throws Exception {
					Log.e("RX_ERROR", error.getMessage());
				}
			});
	}

	public static float dpToPx(float dp) {
		return dp * ((float) resources.getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	} 

	public static float pxToDp(float px) {
		return px / ((float) resources.getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
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
