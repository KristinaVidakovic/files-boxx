package com.filesboxx.ws.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name = "BELONGS_FOLDER_USER")
public class BelongsFolderUser {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "BFU_ID")
	private String bfuId;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "FOLDER_ID")
	private String folderId;
	@Column(name = "DELETED")
	private Boolean deleted;
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User user;
	
	@OneToOne(targetEntity = Folder.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "FOLDER_ID", referencedColumnName = "FOLDER_ID", insertable = false, updatable = false)
	private Folder folder;
	
	public BelongsFolderUser() {
		// TODO Auto-generated constructor stub
	}

	public BelongsFolderUser(String bfuId, String userId, String folderId) {
		super();
		this.bfuId = bfuId;
		this.userId = userId;
		this.folderId = folderId;
	}

	public String getBfuId() {
		return bfuId;
	}

	public void setBfuId(String bfuId) {
		this.bfuId = bfuId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
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
