package com.suleyman.vkclient.module.base;

import com.hannesdorfmann.mosby3.mvp.*;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.suleyman.vkclient.module.broadcast.NetworkStateChangeReceiver;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public abstract class BaseActivity<V extends MvpView, P extends MvpBasePresenter> extends MvpActivity<V,P> {
	
	@ViewInject(R.id.toolbar)
	private Toolbar toolbar;
	
	protected Toolbar getToolbar() {	
		return toolbar;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		registerViewInjecter();

		initToolbar();
		
		onCreateActivity(savedInstanceState);
	}
	
	private void registerViewInjecter() {
		x.view().inject(this);
	}

	private void initToolbar() {
		if (toolbar != null) {
			
			setSupportActionBar(toolbar);
		}
	}
	
	public abstract void onCreateActivity(Bundle savedInstanceState);
}
