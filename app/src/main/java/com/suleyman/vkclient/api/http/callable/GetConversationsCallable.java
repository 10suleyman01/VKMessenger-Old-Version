package com.suleyman.vkclient.api.http.callable;

import com.suleyman.vkclient.api.http.*;
import com.suleyman.vkclient.api.object.conversations.*;
import java.util.*;

import io.reactivex.Flowable;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class GetConversationsCallable implements Callable<ObjectConversations> {

	private VKRequest request;

	public GetConversationsCallable() {
		this.request = VKRequest.set("messages.getConversations", "filter=all", "count=200", "extended=1" , "fields=photo_200_orig,online,sex");
	}

	@Override
	public ObjectConversations call() throws Exception {

		ObjectConversations objectConversations = VKRestClient.get(request, ObjectConversations.class);

		ResponseConversations response = objectConversations.getResponse();

		if (response != null) {
			ArrayList<ItemConversation> items = response.getItems();
			ArrayList<ProfileConversation> profiles = response.getProfiles();
			ArrayList<GroupConversation> groups = response.getGroups();

			Map<Long,ProfileConversation> mapProfiles = new HashMap<>();
			if (profiles != null) {
				for (ProfileConversation profile : profiles) {
					mapProfiles.put(profile.getId(), profile);
				}
			}

			Map<Long, GroupConversation> mapGroups = new HashMap<>();
			if (groups != null) {
				for (GroupConversation group: groups) {
					mapGroups.put(group.getId(), group);
				}
			}

			if (items != null)
			for (ItemConversation item : items) {					
				SubItemConversation subItem = item.getConversation();
				SubItemConversation.Peer peer = subItem.getPeer();
				switch (peer.type) {
					case "chat": {

							ConversationLastMessage lastMessage = item.getLastMessage();
							ConversationLastMessage.Action action = lastMessage.getAction();

							if (action != null) {

								ProfileConversation profile = mapProfiles.get(action.memberId);

								switch (action.type) {

									case "chat_kick_user": {
											int sex = profile.getSex();
											String kicked = sex == 1 ? "покинула" : "покинул";
											lastMessage.setText(String.format("%s %s", profile.getTitle(), kicked + " беседу"));
											item.setLastMessage(lastMessage);
										} break;

									case "chat_invite_user": {
											int sex = profile.getSex();
											String invited = sex == 1 ? "вернулась" : "вернулся";
											lastMessage.setText(String.format("%s %s", profile.getTitle(), invited + " в беседу"));
											item.setLastMessage(lastMessage);
										} break;
									case "chat_title_update": {
											profile = mapProfiles.get(lastMessage.getFromId());
											int sex = profile.getSex();
											String changed = sex == 1 ? "поменяла" : "поменял";
											lastMessage.setText(String.format("%s %s", profile.getTitle(), changed + " название беседы на " + action.text));
											item.setLastMessage(lastMessage);
										} break;
								}

							}
							ChatSettings chatSettings = subItem.getChatSettings();
							item.setTitle(chatSettings.getTitle());
							ChatSettings.Photo photo = chatSettings.getPhoto();
							if (photo != null)  {
								item.setPhoto(photo.getPhoto200());
							}
							long id = item.getLastMessage().getFromId();
							ProfileConversation profileBody = mapProfiles.get(id);
							item.setBodyPhoto(profileBody.getPhoto());

						} break;
					case "user": {
							ConversationLastMessage lastMessage = item.getLastMessage();

							ProfileConversation profile = mapProfiles.get(lastMessage.getPeerId());
							if (lastMessage.isOut()) {
								item.setBodyPhoto(mapProfiles.get(lastMessage.getFromId()).getPhoto());									
							}

							item.setTitle(profile.getTitle());
							item.setOnline(profile.isOnline());
							item.setPhoto(profile.getPhoto());

						} break;
					case "group": {
							String peerId = Long.toString(item.getLastMessage().getPeerId());
							GroupConversation group = mapGroups.get(Long.parseLong(peerId.substring(1)));
							item.setTitle(group.getName());
							item.setPhoto(group.getPhoto());
						} break;
				}
			}

		}
		return objectConversations;
	}

}
