package com.suleyman.vkclient.module.fragment.friends;

import android.support.v7.widget.*;
import android.widget.*;
import com.suleyman.vkclient.api.object.friends.*;
import org.greenrobot.eventbus.*;
import org.xutils.view.annotation.*;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.event.NetworkStateChangeEvent;
import com.suleyman.vkclient.module.base.BaseFragment;
import com.suleyman.vkclient.module.fragment.friends.adapter.FriendsAdapter;
import com.suleyman.vkclient.util.UEventBus;

@ContentView(R.layout.fragment_friends)
public class FriendsFragment extends BaseFragment<FriendsView, FriendsPresenter> implements FriendsView {

	@ViewInject(R.id.loadingListFriends)
	private ProgressBar loadingList;

	@ViewInject(R.id.recyclerViewFriends)
	private RecyclerView recyclerView;

	@ViewInject(R.id.refreshFriends)
	private SwipeRefreshLayout refreshFriends;

	private FriendsAdapter adapter;

	@Override
	public FriendsPresenter createPresenter() {
		return new FriendsPresenter();
	}

	public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        return fragment;
    }

	@Override
	public void onCreateFragment(Bundle savedInstanceState) {
		/** initializing recycler view */
		initRecyclerView();
		
		/** register event bus */
		UEventBus.register(this);

		/** start presenter for loading friends */
		presenter.loadFriends();
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onNetworkStateChange(NetworkStateChangeEvent event) {
		if (event.getState() == NetworkStateChangeEvent.State.CONNECTED) {
			/** start presenter for loading friends */
			presenter.loadFriends();
		}
	}
	
	private void initRecyclerView() {

		refreshFriends.setColorSchemeResources(R.color.primary);
		refreshFriends.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
				@Override
				public void onRefresh() {
					presenter.loadFriends();
				}	
			});

		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);
	}

	@Override
	public void showLoading(boolean loading) {
		if (loading) loadingList.setVisibility(View.VISIBLE);
		else loadingList.setVisibility(View.GONE);
	}

	@Override
	public void setFriends(ObjectFriends objectFriends) {
		if (objectFriends != null && objectFriends.getResponse() != null) {
			ResponseFriends response = objectFriends.getResponse();
			
			adapter = new FriendsAdapter(response.getItems());
			recyclerView.setAdapter(adapter);
		}

		if (refreshFriends != null && refreshFriends.isRefreshing()) {
			refreshFriends.setRefreshing(false);
		}
	}

	@Override
	public void showError(Throwable error) {
		StackTraceElement[] stackTrace = error.getStackTrace();
		int length = stackTrace.length;
		Toast.makeText(getActivity(), error.getMessage() + " " + stackTrace[length-1].getLineNumber(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		presenter.dispose();
		
		/** unregister event bus */
		UEventBus.unregister(this);
	}
}
