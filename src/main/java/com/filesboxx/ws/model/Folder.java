package com.filesboxx.ws.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "FOLDER")
public class Folder implements OneOfFolder{

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@ApiModelProperty(example = "11e06451-5fb8-46c4-9050-5c5f605320c6")
	@Column(name = "FOLDER_ID")
	private String folderId;
	@ApiModelProperty(example = "New folder")
	@Column(name = "NAME")
	private String name;
	@ApiModelProperty(example = "false")
	@Column(name = "DELETED")
	private Boolean deleted;
	
	public Folder() {
		// TODO Auto-generated constructor stub
	}

	public Folder(String folderId, String name) {
		super();
		this.folderId = folderId;
		this.name = name;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
