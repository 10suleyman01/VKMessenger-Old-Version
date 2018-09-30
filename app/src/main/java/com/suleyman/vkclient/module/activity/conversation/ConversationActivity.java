package com.suleyman.vkclient.module.activity.conversation;

import org.xutils.view.annotation.*;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.object.conversationHistory.ObjectConversationHistory;
import com.suleyman.vkclient.module.base.BaseActivity;
import com.suleyman.vkclient.util.DisposableManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import com.suleyman.vkclient.api.object.conversationHistory.ResponseConversationHistory;
import android.support.v7.widget.LinearLayoutManager;
import com.suleyman.vkclient.module.activity.conversation.adapter.MessagesAdapter;
import android.widget.Toast;

@ContentView(R.layout.activity_conversation)
public class ConversationActivity extends BaseActivity<ConversationView, ConversationPresenter> implements ConversationView {

	@ViewInject(R.id.loadingMessages)
	private ProgressBar loadingMessage;
	
	@ViewInject(R.id.recyclerViewMessages)
	private RecyclerView recyclerViewMessages;
	
	private MessagesAdapter adapter;
	
	private long peerId;
	
	@Override
	public ConversationPresenter createPresenter() {
		return new ConversationPresenter();
	}

	@Override
	public void onCreateActivity(Bundle savedInstanceState) {
		
		/** init user toolbar */
		initUserToolbar(getIntent().getExtras());
		
		/** init recyclerview */
		initRecyclerView();
		
		/** loading messages*/
		presenter.loadMessages(peerId, 0);
		
	}
	
	private void initRecyclerView() {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setReverseLayout(true);
		recyclerViewMessages.setLayoutManager(linearLayoutManager);
	}

	@Override
	public void showLoading(boolean isLoading) {
		if (isLoading) loadingMessage.setVisibility(View.VISIBLE);
		else loadingMessage.setVisibility(View.GONE);
	}

	@Override
	public void setMessages(ObjectConversationHistory objectMessages) {
		if (objectMessages != null) {
			ResponseConversationHistory response = objectMessages.getResponse();
			if (response != null) {
				adapter = new MessagesAdapter(response.getLastMessage());
				recyclerViewMessages.setAdapter(adapter);
			}
		}
	}

	@Override
	public void showError(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private void initUserToolbar(Bundle extras) {
		String title = extras.getString("title");
		String online = extras.getString("online");
		final String photoUrl = extras.getString("photoUrl");
		peerId = extras.getLong("peerId");
		
		setTitle(title);
		getSupportActionBar().setSubtitle(online);

		DisposableManager.add(
			Observable.fromCallable(new Callable<Drawable>(){
					@Override
					public Drawable call() throws Exception {
						return Glide.with(getApplicationContext()).
							asDrawable().
							apply(new RequestOptions().circleCropTransform()).
							load(photoUrl).
							into(50, 50).
							get();
					}
				}).
			subscribeOn(Schedulers.io()).
			observeOn(AndroidSchedulers.mainThread()).
			subscribe(new Consumer<Drawable>(){
					@Override
					public void accept(Drawable photo) throws Exception {
						getSupportActionBar().setDisplayUseLogoEnabled(true);
						getSupportActionBar().setIcon(photo);
					}
				}, new Consumer<Throwable>(){
					@Override
					public void accept(Throwable error) throws Exception {
						error.printStackTrace();
					}
				})
		);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		DisposableManager.dispose();
	}
}
