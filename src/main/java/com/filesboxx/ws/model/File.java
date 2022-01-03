package com.filesboxx.ws.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "FILE")
public class File implements OneOfFile{

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@ApiModelProperty(example = "cb61913d-d44f-4378-9488-5bcda7e2dbbb")
	@Column(name = "FILE_ID")
	private String fileId;
	@ApiModelProperty(example = "FilesBoxx - servisi.xlsx", hidden = false)
	@Column(name = "NAME")
	private String name;
	@Column(name = "DATA")
	@Lob
	private byte[] data;
	@ApiModelProperty(example = "false")
	@Column(name = "DELETED")
	private Boolean deleted;
	
	public File() {
		// TODO Auto-generated constructor stub
	}

	public File(String fileId, String name, byte[] data) {
		super();
		this.fileId = fileId;
		this.name = name;
		this.data = data;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
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
