package com.suleyman.vkclient.module.base;

import android.view.*;
import com.hannesdorfmann.mosby3.mvp.*;

import android.os.Bundle;
import org.xutils.x;

public abstract class BaseFragment<V extends MvpView, P extends MvpBasePresenter<V>> extends MvpFragment<V,P> {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return x.view().inject(this, inflater, container);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		onCreateFragment(savedInstanceState);
	}

	public abstract void onCreateFragment(Bundle savedInstanceState);
}
