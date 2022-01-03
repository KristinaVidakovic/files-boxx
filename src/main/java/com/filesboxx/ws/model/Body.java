package com.filesboxx.ws.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiModelProperty;

public class Body {

	@ApiModelProperty(example = "cb61913d-d44f-4378-9488-5bcda7e2dbbb")
	private String fileId;
	@ApiModelProperty(example = "11e06451-5fb8-46c4-9050-5c5f605320c6")
	private String folderId;
	@ApiModelProperty(example = "null")
	private String userId;
	
	public Body() {
		// TODO Auto-generated constructor stub
	}

	public Body(String fileId, String folderId, String userId) {
		super();
		this.fileId = fileId;
		this.folderId = folderId;
		this.userId = userId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
	
}
