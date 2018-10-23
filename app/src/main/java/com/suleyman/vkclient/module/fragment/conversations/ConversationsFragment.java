package com.suleyman.vkclient.module.fragment.conversations;

import android.support.v7.widget.*;
import android.widget.*;
import com.suleyman.vkclient.api.event.*;
import com.suleyman.vkclient.api.object.conversations.*;
import io.reactivex.functions.*;
import org.greenrobot.eventbus.*;
import org.xutils.view.annotation.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.module.activity.conversation.ConversationActivity;
import com.suleyman.vkclient.module.base.BaseFragment;
import com.suleyman.vkclient.module.fragment.conversations.adapter.ConversationsAdapter;
import com.suleyman.vkclient.util.UEventBus;
import io.reactivex.Observable;
import java.util.ArrayList;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import com.suleyman.vkclient.util.DisposableManager;

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

		/** register event bus */
		UEventBus.register(this);

		/** start presenter for loading conversations */
		presenter.loadConversations();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onNetworkStateChange(NetworkStateChangeEvent event) {
		if (event.getState() == NetworkStateChangeEvent.State.CONNECTED) {
			/** start presenter for loading conversations */
			presenter.loadConversations();
		}
	}

	public static ConversationsFragment newInstance() {
		return new ConversationsFragment();
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

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onUpdateConversation(UpdateMessageEvent event) {

		SubItemConversation conversation = event.getConversation();
		ConversationLastMessage newMessage = event.getLastMessage();

		ArrayList<ItemConversation> items = adapter.getItems();
		for (int i = 0; i < items.size(); i++) {

			ItemConversation item = items.get(i);

			if (newMessage.getPeerId() == item.getLastMessage().getPeerId()) {
				item.setConversation(conversation);
				item.setLastMessage(newMessage);

				adapter.setItem(i, item, true);
				recyclerView.getLayoutManager().scrollToPosition(0);
			}
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onTyping(final TypingEvent event) {
		final ArrayList<ItemConversation> messages = adapter.getItems();
		DisposableManager.add("set_typing_status", Observable.fromIterable(messages).
			filter(new Predicate<ItemConversation>(){
				@Override
				public boolean test(ItemConversation conversation) throws Exception {
					return conversation.getLastMessage().getPeerId() == event.getId();
				}
			}).subscribe(new Consumer<ItemConversation>(){
				@Override
				public void accept(final ItemConversation itemConversation) throws Exception {		
					itemConversation.getLastMessage().setIsAttachment(true);
					itemConversation.getLastMessage().setText("печатает...");
					adapter.setConversation(event.getId(), messages, null);
				}
			}, new Consumer<Throwable>(){
				@Override
				public void accept(Throwable error) throws Exception {
					error.printStackTrace();
				}
			}, new Action(){
				@Override
				public void run() throws Exception {
					Observable.fromIterable(messages).
						delay(2, TimeUnit.SECONDS, Schedulers.io()).
						subscribe(new Consumer<ItemConversation>(){
							@Override
							public void accept(ItemConversation itemConversation) throws Exception {
								adapter.setConversation(event.getId(), messages, null);
								DisposableManager.dispose("set_typing_status");
							}					
						});
				}		
			}));

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

							String type = item.getConversation().getPeer().type;
							if (type.equals("chat")) {
								intent.putExtra("subTitle", "Участников " + item.getConversation().getChatSettings().getCountMembers());
							} else if (type.equals("group")) {
								intent.putExtra("subTitle", "Сообщество");
							} else {
								String online = (item.isOnline() ? "В сети" : "Не в сети");
								intent.putExtra("subTitle", online);
							}
							intent.putExtra("peerId", item.getLastMessage().getPeerId());
							intent.putExtra("photoUrl", item.getPhoto());
							getActivity().startActivity(intent);
						}
					});
				recyclerView.setAdapter(adapter);
			}
		}
	}

	@Override
	public void showError(Throwable error) {
		error.printStackTrace();
		Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		presenter.dispose();

		/** unregister event bus */
		UEventBus.unregister(this);
	}
}
