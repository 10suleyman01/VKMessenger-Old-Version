package com.suleyman.vkclient.module.fragment.conversations;

import android.support.v7.widget.*;
import android.widget.*;
import com.suleyman.vkclient.api.object.conversations.*;
import org.xutils.view.annotation.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.module.base.BaseFragment;
import com.suleyman.vkclient.module.fragment.conversations.adapter.ConversationsAdapter;
import com.suleyman.vkclient.util.DisposableManager;
import com.suleyman.vkclient.module.activity.conversation.ConversationActivity;
import com.suleyman.vkclient.app.VKApp;

@ContentView(R.layout.fragment_list)
public class ConversationsFragment extends BaseFragment<ConversationsView, ConversationsPresenter> implements ConversationsView {

	@ViewInject(R.id.loadingList)
	private ProgressBar loadingList;

	@ViewInject(R.id.recyclerView)
	private RecyclerView recyclerView;
	
	private ConversationsAdapter adapter;

	@Override
	public ConversationsPresenter createPresenter() {
		return new ConversationsPresenter();
	}

	@Override
	public void onCreateFragment(Bundle savedInstanceState) {

		/** initializing recycler view */
		initRecyclerView();

	}
	
	public static ConversationsFragment newInstance() {
        ConversationsFragment fragment = new ConversationsFragment();
        return fragment;
    }

	@Override
	public void onStart() {
		super.onStart();
		
		presenter.loadConversations();
	}

	@Override
	public void onStop() {
		super.onStop();
		
		DisposableManager.dispose();
	}

	private void initRecyclerView() {
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);	
	}

	@Override
	public void showLoading(boolean loading) {
		if (loading) loadingList.setVisibility(View.VISIBLE);
		else loadingList.setVisibility(View.GONE);
	}

	@Override
	public void setConversations(ObjectConversations objectConversations) {
		if (objectConversations != null) {
			ResponseConversations response = objectConversations.getResponse();
			if (response != null) {
				adapter = new ConversationsAdapter(response.getItems());
				adapter.setItemOnClickListener(new ConversationsAdapter.ItemOnClickListener<ItemConversation>(){
						@Override
						public void onClick(ItemConversation item, int pos) {
							Intent intent = new Intent(getActivity(), ConversationActivity.class);
							intent.putExtra("title", item.getTitle());
							intent.putExtra("photoUrl", item.getPhoto());
							intent.putExtra("online", item.isOnline() ? "online" : "oflline");
							intent.putExtra("peerId", item.getLastMessage().getPeerId());
							getActivity().startActivity(intent);
						}
				});
				recyclerView.setAdapter(adapter);
			}
		}
	}

	@Override
	public void showError(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

}
