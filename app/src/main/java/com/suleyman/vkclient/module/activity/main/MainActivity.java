package com.suleyman.vkclient.module.activity.main;

import android.support.v4.app.*;
import android.view.*;
import org.xutils.view.annotation.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.object.account.VKAccountManager;
import com.suleyman.vkclient.module.activity.login.LoginActivity;
import com.suleyman.vkclient.module.base.BaseActivity;
import com.suleyman.vkclient.module.fragment.conversations.ConversationsFragment;
import com.suleyman.vkclient.module.fragment.friends.FriendsFragment;
import com.suleyman.vkclient.module.service.LongPollService;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity<MainView, MainPresenter> implements BottomNavigationView.OnNavigationItemSelectedListener {

	private static String FRIENDS_TAG = "friends";
	private static String CONVERSASTIONS_TAG = "conversations";
	
	@ViewInject(R.id.bottomNavigationView)
	private BottomNavigationView bottomNavigationView;

	private FragmentManager fragmentManager;

	@Override
	public MainPresenter createPresenter() {
		return new MainPresenter();
	}

	@Override
	public void onCreateActivity(Bundle savedInstanceState) {

		fragmentManager = getSupportFragmentManager();

		bottomNavigationView.setOnNavigationItemSelectedListener(this);
		bottomNavigationView.setSelectedItemId(R.id.messages);
		
		/** start long poll server */
		Intent intent = new Intent(this, LongPollService.class);
		startService(intent);
		/** end */
		
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {

		/** set toolbar title */
		setTitle(item.getTitle());

		switch (item.getItemId()) {
			case R.id.friends: addFragment(FriendsFragment.newInstance(), FRIENDS_TAG); break;
			case R.id.messages: addFragment(ConversationsFragment.newInstance(), CONVERSASTIONS_TAG); break;
		}

		return true;
	}

	private void addFragment(Fragment fragment, String tag) {
		
		Fragment findFragment = fragmentManager.findFragmentByTag(tag);
		
		if (findFragment != null) {
			return;
		}
		
		fragmentManager.beginTransaction().
			setCustomAnimations(R.anim.slide_left, R.anim.slide_right).
			replace(R.id.container, fragment, tag).
			commit();
			
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

}
