package com.suleyman.vkclient.module.activity.conversation;

import android.support.v7.widget.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import com.suleyman.vkclient.api.event.*;
import com.suleyman.vkclient.api.http.callable.*;
import com.suleyman.vkclient.api.object.conversationHistory.*;
import com.suleyman.vkclient.api.object.doc.*;
import com.suleyman.vkclient.module.activity.conversation.listener.*;
import com.suleyman.vkclient.util.*;
import java.io.*;
import org.greenrobot.eventbus.*;
import org.xutils.view.annotation.*;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.ObservableProvider;
import com.suleyman.vkclient.api.http.VKRestClient;
import com.suleyman.vkclient.api.object.conversations.ConversationLastMessage;
import com.suleyman.vkclient.app.VKApp;
import com.suleyman.vkclient.module.activity.conversation.adapter.MessagesAdapter;
import com.suleyman.vkclient.module.base.BaseActivity;
import com.vansuita.library.Icon;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import okhttp3.Response;
import org.json.JSONObject;

@ContentView(R.layout.activity_conversation)
public class ConversationActivity extends BaseActivity<ConversationView, ConversationPresenter> implements ConversationView {

	@ViewInject(R.id.loadingConversations)
	private ProgressBar loadingConversations;

	@ViewInject(R.id.recyclerViewConversations)
	private RecyclerView recyclerViewConversations;

	@ViewInject(R.id.typeMessage)
	private EditText inputMessage;

	@ViewInject(R.id.btnSend)
	private ImageView btnSend;

	@ViewInject(R.id.btnAttachment)
	private ImageView btnAttachment;

	@ViewInject(R.id.attachmentlayout)
	private LinearLayout attachmentlayout;

	@ViewInject(R.id.divider)
	private View divider;
	
	private MessagesAdapter adapter;
	private long peerId;

	@Override
	public ConversationPresenter createPresenter() {
		return new ConversationPresenter();
	}

	@Override
	public void onCreateActivity(Bundle savedInstanceState) {

		/** init input message */
		initInputMessage();

		/** init attachment button */
		initAttachmentButton();

		/** init recycler view */
		initRecyclerView();

		/** register event bus */
		UEventBus.register(this);

		/** init user toolbar */
		initUserToolbar(getIntent().getExtras());

		/** loading messages */
		presenter.loadMessages(peerId, 0);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onNetworkStateChange(NetworkStateChangeEvent event) {
		if (event.getState() == NetworkStateChangeEvent.State.CONNECTED) {
			/** loading messages */
			presenter.loadMessages(peerId, 0);
		}
	}

	private void initInputMessage() {

		Icon.on(btnSend).color(R.color.gray_color).icon(R.drawable.outline_mic_none_white_36).put();

		inputMessage.addTextChangedListener(new TextWatcher(){
				@Override
				public void beforeTextChanged(CharSequence text, int p2, int p3, int p4) {
				}

				@Override
				public void onTextChanged(CharSequence text, int p2, int p3, int p4) {
				}

				@Override
				public void afterTextChanged(Editable e) {
					if (!e.toString().isEmpty()) {
						Icon.on(btnSend).color(VKApp.getColor(R.color.primary)).icon(R.drawable.baseline_send_white_36).put();
						btnSend.setOnClickListener(new SendMessage(inputMessage, peerId));
					} else {
						Icon.on(btnSend).color(R.color.gray_color).icon(R.drawable.outline_mic_none_white_36).put();
						btnSend.setOnClickListener(new SendAudioMessage());
					}
				}
			});
	}

	private void initAttachmentButton() {

		Icon.on(btnAttachment).color(R.color.gray_color).icon(R.drawable.round_attach_file_white_48).put();
		btnAttachment.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
				}
			});
	}

	private void initRecyclerView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setReverseLayout(true);
		recyclerViewConversations.setLayoutManager(layoutManager);
	}

	@Override
	public void showLoading(boolean isLoading) {
		if (isLoading) loadingConversations.setVisibility(View.VISIBLE);
		else loadingConversations.setVisibility(View.GONE);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onUpdate(UpdateMessageEvent event) {
		ConversationLastMessage newMessage = event.getLastMessage();
		if (newMessage.getPeerId() == peerId) {
			adapter.addMessage(newMessage);
			recyclerViewConversations.getLayoutManager().scrollToPosition(0);
		}
	}

	@Override
	public void setMessages(ObjectConversationHistory objectMessages) {
		if (objectMessages != null && objectMessages.getResponse() != null) {
			ResponseConversationHistory response = objectMessages.getResponse();
			ArrayList<ConversationLastMessage> lastMessages = response.getLastMessages();
			adapter = new MessagesAdapter(this, lastMessages);
			recyclerViewConversations.setAdapter(adapter);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) return;

		final File file = new File(data.getDataString());

		ObservableProvider.observableFromCallable(new GetDocumentUploadServerCallable("doc", peerId)).
			subscribe(new Consumer<String>(){
				@Override
				public void accept(String response) throws Exception {
					JSONObject responseUploadUrl = new JSONObject(response).getJSONObject("response");
					String uploadUrl = responseUploadUrl.getString("upload_url");
					VKRestClient.uploadFile(uploadUrl, file, new VKRestClient.OnUploadCallback(){
							@Override
							public void onUpload(Response response) {
								try {
									JSONObject object = new JSONObject(response.body().string());
									String fileData = object.getString("file");
									ObservableProvider.observableFromCallable(new DocumentSaveCallable(fileData, file.getName())).
										subscribe(new Consumer<ObjectDocument>(){ 
											@Override
											public void accept(ObjectDocument objectDocument) throws Exception {
												ArrayList<ItemDocument> documents = objectDocument.getDocuments();
												StringBuilder params = new StringBuilder();
												for (ItemDocument document : documents) {
													params.append("doc" + document.getOwnerId() + "_" + document.getId() + ",");
												}
												ObservableProvider.observableFromCallable(
													new SendMessageCallable(peerId, inputMessage.getText().toString(), params.toString())
												).subscribe();
											}				
										});
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onError(IOException error) {
								error.printStackTrace();
							}
						});
				}
			});
	}

	@Override
	public void showError(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private void initUserToolbar(Bundle extras) {

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		getToolbar().setTitleMarginStart(14);

		String title = extras.getString("title");
		String subTitle = extras.getString("subTitle");
		String photoUrl = extras.getString("photoUrl");

		peerId = extras.getLong("peerId");

		setTitle(title);
		getSupportActionBar().setSubtitle(subTitle);

		DisposableManager.add("get_photo", 
							  ObservableProvider.observableFromCallable(new GetDrawableCallable(this, photoUrl, 50, 50)).
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_conversation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		/** unregister event bus */
		UEventBus.unregister(this);

		presenter.dispose();

		DisposableManager.dispose("get_photo");	

	}
}
