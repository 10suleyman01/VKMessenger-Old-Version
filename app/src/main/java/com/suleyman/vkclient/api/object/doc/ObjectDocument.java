package com.suleyman.vkclient.api.object.doc;


import com.google.gson.annotations.*;

import java.util.ArrayList;

public class ObjectDocument {
	
	@SerializedName("response")
	@Expose
	private ArrayList<ItemDocument> documents;
	
	public void setDocuments(ArrayList<ItemDocument> documents) {
		this.documents = documents;
	}

	public ArrayList<ItemDocument> getDocuments() {
		return documents;
	}
}
