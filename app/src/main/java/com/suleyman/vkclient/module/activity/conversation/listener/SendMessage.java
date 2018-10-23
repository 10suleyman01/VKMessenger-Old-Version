package com.suleyman.vkclient.module.activity.conversation.listener;

import android.view.View;
import android.widget.EditText;
import com.suleyman.vkclient.api.ObservableProvider;
import com.suleyman.vkclient.api.http.callable.SendMessageCallable;

public class SendMessage implements View.OnClickListener {

	private EditText inputMessage;
	private long peerId;

	public SendMessage(EditText inputMessage, long peerId) {
		this.inputMessage = inputMessage;
		this.peerId = peerId;
	}

	@Override
	public void onClick(View view) {

		String text = inputMessage.getText().toString();
		if (text != null && !text.isEmpty()) {
			ObservableProvider.observableFromCallable(new SendMessageCallable(peerId, text, "")).
				subscribe();
			inputMessage.setText("");
		}

	}

}
