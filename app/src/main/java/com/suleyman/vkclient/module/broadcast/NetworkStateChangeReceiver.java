package com.suleyman.vkclient.module.broadcast;

import android.content.*;
import android.net.*;

import android.util.Log;
import com.suleyman.vkclient.api.event.NetworkStateChangeEvent;
import com.suleyman.vkclient.util.UEventBus;

public class NetworkStateChangeReceiver extends BroadcastReceiver {
	
	private ConnectivityManager connectivityManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		
		if (netInfo != null) {
			NetworkInfo.State state = netInfo.getState();
			if (state == NetworkInfo.State.CONNECTED) {
				UEventBus.post(new NetworkStateChangeEvent(NetworkStateChangeEvent.State.CONNECTED));
			}
			if (state == NetworkInfo.State.DISCONNECTED) {
				UEventBus.post(new NetworkStateChangeEvent(NetworkStateChangeEvent.State.DISCONNECTED));
			}
		}
		
	}

}
