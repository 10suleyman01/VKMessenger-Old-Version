package com.suleyman.vkclient.module.activity.main;

import android.content.*;
import android.support.v4.app.*;
import android.view.*;
import com.suleyman.vkclient.module.service.*;
import org.greenrobot.eventbus.*;
import org.xutils.view.annotation.*;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.event.NetworkStateChangeEvent;
import com.suleyman.vkclient.api.object.account.VKAccountManager;
import com.suleyman.vkclient.module.activity.login.LoginActivity;
import com.suleyman.vkclient.module.base.BaseActivity;
import com.suleyman.vkclient.module.broadcast.NetworkStateChangeReceiver;
import com.suleyman.vkclient.module.fragment.conversations.ConversationsFragment;
import com.suleyman.vkclient.module.fragment.friends.FriendsFragment;
import com.suleyman.vkclient.util.UEventBus;
import com.suleyman.vkclient.module.fragment.profile.ProfileFragment;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity<MainView, MainPresenter> implements BottomNavigationView.OnNavigationItemSelectedListener {

	private static String FRIENDS_TAG = "friends";
	private static String CONVERSASTIONS_TAG = "conversations";
	private static String PROFILE_TAG = "profile";
	private static String SETINGS_TAG = "settings";


	@ViewInject(R.id.bottomNavigationView)
	private BottomNavigationView bottomNavigationView;

	private FragmentManager fragmentManager;
	private NetworkStateChangeReceiver receiverNetworkStateChange;

	@Override
	public void onCreateActivity(Bundle savedInstanceState) {

		fragmentManager = getSupportFragmentManager();

		if (bottomNavigationView != null) {
			bottomNavigationView.setOnNavigationItemSelectedListener(this);
			bottomNavigationView.setSelectedItemId(R.id.messages);
		}

		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

		receiverNetworkStateChange = new NetworkStateChangeReceiver();
	}

	@Override
	protected void onStart() {
		super.onStart();

		/** register event bus*/
		UEventBus.register(this);

		/** register broadcast(for check state connection)*/
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(receiverNetworkStateChange, intentFilter);

	}

	@Override
	protected void onStop() {
		super.onStop();

		/** unregister event bus*/
		UEventBus.unregister(this);

		/** unregister broadcast(for check state connection)*/
		unregisterReceiver(receiverNetworkStateChange);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onNetworkStateChange(NetworkStateChangeEvent event) {
		NetworkStateChangeEvent.State state = event.getState();
		if (state == NetworkStateChangeEvent.State.CONNECTED) {
			startLongPollServer();
		} else if (state == NetworkStateChangeEvent.State.DISCONNECTED) {
			stopLongPollServer();
		}
	}

	private void startLongPollServer() {
		/** start long poll server */
		Intent longPollServer = new Intent(this, LongPollService.class);
		startService(longPollServer);

		/** start long poll server for updating conversations */
		Intent longPollUpdatingConversations = new Intent(this, LongPollServerConversation.class);
		startService(longPollUpdatingConversations);
	}

	private void stopLongPollServer() {
		/** stop long poll server */
		Intent longPollServer = new Intent(this, LongPollService.class);
		stopService(longPollServer);

		/** stop long poll server for updating conversations */
		Intent longPollServerConversationIntent = new Intent(this, LongPollServerConversation.class);
		stopService(longPollServerConversationIntent);
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {

		/** set toolbar title */
		setTitle(item.getTitle());

		switch (item.getItemId()) {
				case R.id.friends: addFragment(FriendsFragment.newInstance(), FRIENDS_TAG); break;
				case R.id.messages: addFragment(ConversationsFragment.newInstance(), CONVERSASTIONS_TAG); break;
				case R.id.profile: addFragment(ProfileFragment.newInstance(), PROFILE_TAG); break;
		} // test git

		return true;
	}

	private void addFragment(Fragment fragment, String tag) {

		Fragment findFragment = fragmentManager.findFragmentByTag(tag);

		if (findFragment != null) {
			return;
		}

		fragmentManager.beginTransaction().
			replace(R.id.container, fragment, tag).
			commit();

	}

	@Override
	public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
		super.onMultiWindowModeChanged(isInMultiWindowMode);

		bottomNavigationView.setSelectedItemId(bottomNavigationView.getSelectedItemId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.logout) {
			toLogin();
		}
		return true;
	}

	private void toLogin() {

		/** checking for logout */
		if (VKAccountManager.isLogined()) {

			/** delete token and password */
			VKAccountManager.logout();

			/** to login activity */
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);

			/** finish MainActivity*/
			finish();

		}
	}

	@Override
	public MainPresenter createPresenter() {
		return new MainPresenter();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		stopLongPollServer();
	}

}
