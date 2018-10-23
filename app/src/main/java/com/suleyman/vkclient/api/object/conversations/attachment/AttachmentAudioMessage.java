package com.suleyman.vkclient.api.object.conversations.attachment;

import com.google.gson.annotations.*;

public class AttachmentAudioMessage {

	@SerializedName("id")
	@Expose
	private long id;

	@SerializedName("owner_id")
	@Expose
	private long ownerId;

	@SerializedName("duration")
	@Expose
	private int duration;

	@SerializedName("waveform")
	@Expose
	private int[] waveForm;

	@SerializedName("link_ogg")
	@Expose
	private String linkOgg;

	@SerializedName("link_mp3")
	@Expose
	private String linkMp3;

	@SerializedName("access_key")
	@Expose
	private String accessKey;	

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public void setWaveForm(int[] waveForm) {
		this.waveForm = waveForm;
	}

	public int[] getWaveForm() {
		return waveForm;
	}

	public void setLinkOgg(String linkOgg) {
		this.linkOgg = linkOgg;
	}

	public String getLinkOgg() {
		return linkOgg;
	}

	public void setLinkMp3(String linkMp3) {
		this.linkMp3 = linkMp3;
	}

	public String getLinkMp3() {
		return linkMp3;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getAccessKey() {
		return accessKey;
	}
}
