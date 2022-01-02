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
@Table(name = "BELONGS_FILE_FOLDER")
public class BelongsFileFolder {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "BFF_ID")
	private String bffId;
	@Column(name = "FILE_ID")
	private String fileId;
	@Column(name = "FOLDER_ID")
	private String folderId;
	@Column(name = "DELETED")
	private Boolean deleted;
	
	@OneToOne(targetEntity = File.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "FILE_ID", referencedColumnName = "FILE_ID", insertable = false, updatable = false)
	private File file;
	
	@OneToOne(targetEntity = Folder.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "FOLDER_ID", referencedColumnName = "FOLDER_ID", insertable = false, updatable = false)
	private Folder folder;
	
	public BelongsFileFolder() {
		// TODO Auto-generated constructor stub
	}

	public BelongsFileFolder(String bffId, String fileId, String folderId) {
		super();
		this.bffId = bffId;
		this.fileId = fileId;
		this.folderId = folderId;
	}

	public String getBffId() {
		return bffId;
	}

	public void setBffId(String bffId) {
		this.bffId = bffId;
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
