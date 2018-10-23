package com.suleyman.vkclient.api.object.doc;

import com.google.gson.annotations.*;

public class ItemDocument {
	
	@SerializedName("owner_id")
	@Expose
	private long ownerId;
	
	@SerializedName("id")
	@Expose
	private long id;

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
