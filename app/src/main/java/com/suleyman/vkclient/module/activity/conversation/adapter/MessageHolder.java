package com.suleyman.vkclient.module.activity.conversation.adapter;

import android.view.*;
import android.widget.*;
import com.suleyman.vkclient.api.object.conversations.*;
import com.suleyman.vkclient.api.object.conversations.attachment.*;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.api.ObservableProvider;
import com.suleyman.vkclient.api.http.callable.GetUserCallable;
import com.suleyman.vkclient.api.object.user.ObjectUser;
import com.suleyman.vkclient.module.base.adapter.BaseAdapter;
import com.suleyman.vkclient.module.fragment.photo.PhotoFragment;
import com.suleyman.vkclient.view.AppTextView;
import com.vansuita.library.Icon;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import com.suleyman.vkclient.api.object.user.ItemUser;
import com.suleyman.vkclient.util.DisposableManager;

public class MessageHolder extends BaseAdapter.BaseViewHolder {

	@ViewInject(R.id.chatMessagePhoto)
	private CircleImageView chatMessagePhoto;

	@ViewInject(R.id.containerMessages)
	private LinearLayout containerMessages;

	private ConversationLastMessage message;
	private Context context;
	private SimpleDateFormat messageDateFormat = new SimpleDateFormat("hh:mm");

	private FragmentManager fragmentManager;
	private LayoutInflater inflater;

	public MessageHolder(View view) {
		super(view);
		x.view().inject(this, view);
	}

	public void setup(AppCompatActivity activity, ConversationLastMessage message, Context context) {

		fragmentManager = activity.getSupportFragmentManager();

		this.message = message;
		this.context = context;

		inflater = LayoutInflater.from(context);
	}

	public void addMessages() {

		if (chatMessagePhoto != null)
			chatMessagePhoto.setVisibility(View.GONE);

		RelativeLayout messageView = (RelativeLayout) inflater.inflate(R.layout.message_text, null);

//		TextView messageDate = (TextView) messageView.findViewById(R.id.messageDate);
//		messageDate.setText(messageDateFormat.format(message.getDate()));

		AppTextView messageText = (AppTextView) messageView.findViewById(R.id.messageText);
		if (message.isOut()) {
			messageText.setBackgroundResource(R.drawable.conversation_bg_out);
		} else {
			messageText.setBackgroundResource(R.drawable.conversation_bg_in);
		}

		String text = message.getText();
		if (!text.isEmpty()) {
			messageText.setText(text);
			addToContainer(messageView);
		}
	}

	public void addAttachments() {
		ArrayList<ConversationAttachment> attachments = message.getAttachments();
		if (attachments != null && attachments.size() > 0) {

			final ArrayList<String> photoUrls = new ArrayList<>();
			int index = 0;

			for (ConversationAttachment attachment : attachments) {
				String type = attachment.getType();
				switch (type) {
						case ConversationAttachment.PHOTO: {
							LinearLayout flexLayout = (LinearLayout) 
								inflater.inflate(R.layout.attachment_photo, null);

							FlexboxLayout flexBoxPhotos = (FlexboxLayout) 
								flexLayout.findViewById(R.id.photosView);

							photoUrls.add(attachment.getPhoto().getUrl());

							Bundle arguments = new Bundle();
							arguments.putStringArrayList("urls", photoUrls);

							addPhotos(index, arguments, flexBoxPhotos, attachment.getPhoto());
							index++;
							addToContainer(flexLayout);
						} break;
						case ConversationAttachment.DOC: {

							RelativeLayout documentLayout = (RelativeLayout) inflater.inflate(R.layout.attachment_doc, null);

							TextView documentTitle = (TextView) documentLayout.findViewById(R.id.documentTitle);
							TextView documentSize = (TextView) documentLayout.findViewById(R.id.documentSize);
							ImageView documentIcon = (ImageView) documentLayout.findViewById(R.id.documentIcon);

							AttachmentDocument document = attachment.getDocument();
							documentTitle.setText(document.getTitle());
							documentSize.setText(document.getSize() + " Б * " + document.getExt());

							Icon.on(documentIcon).color(R.color.gray_color).icon(R.drawable.baseline_insert_drive_file_white_36).put();

							addToContainer(documentLayout);

						} break;
				}
			}


		}
	}

	private void addPhotos(int index, final Bundle arguments, final FlexboxLayout flexBoxPhotos, AttachmentPhoto attachmentPhoto) {

		final ImageView photo = new ImageView(context);
		photo.setPadding(0, 4, 0, 4);
		photo.setId(index);

		Glide.with(photo).
			load(attachmentPhoto.getUrl()).
			into(photo);

		flexBoxPhotos.addView(photo);

		photo.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					arguments.putInt("index", photo.getId());
					PhotoFragment.newInstanse(arguments).show(fragmentManager, "photoFragment");
				}
			});
	}

	public void addForwards() {
		ArrayList<ForwardConversation> fwdConversations = message.getFwdConversations();
		if (fwdConversations != null && fwdConversations.size() > 0) {

			LinearLayout forwardLayout = (LinearLayout) inflater.inflate(R.layout.forward_item, null);

			for (ForwardConversation forwardMessage : fwdConversations) {
				addForwardMessage(forwardLayout, forwardMessage);
			}

			addToContainer(forwardLayout);
		}
	}

	private void addForwardMessage(final LinearLayout forwardLayout, ForwardConversation forwardMessage) {

		DisposableManager.add("loadForwardProfile",
							  ObservableProvider.observableFromCallable(new GetUserCallable(forwardMessage.getFromId())).
							  subscribe(new Consumer<ObjectUser>(){
									  @Override
									  public void accept(ObjectUser objectUser) throws Exception {
										  ItemUser user = objectUser.getResponse().get(0);

										  TextView forwardTitle = (TextView) forwardLayout.findViewById(R.id.forwardTitle);
										  forwardTitle.setText(user.getTitle());

										  CircleImageView forwardPhoto = (CircleImageView) forwardLayout.findViewById(R.id.forwardPhoto);
										  Glide.with(context).
											  load(user.getPhoto()).
											  into(forwardPhoto);
									  }
								  }));

		LinearLayout forwardContainer = (LinearLayout) forwardLayout.findViewById(R.id.forwardContainer);

		TextView forwardText = new TextView(context);
		forwardText.setText(forwardMessage.getText());

		forwardContainer.addView(forwardText);
	}

	public void addToContainer(View view) {
		if (containerMessages == null) return; 
		containerMessages.addView(view);
	}

	public void clearView(View view) {
		if (view.getParent() != null) {
			((ViewGroup)view).removeView(view);
			DisposableManager.dispose("loadForwardProfile");
		}
	}

	public void clear() {
		if (containerMessages == null) return; 
		containerMessages.removeAllViews();
	}
}
