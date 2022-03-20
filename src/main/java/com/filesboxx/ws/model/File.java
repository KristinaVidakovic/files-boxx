package com.filesboxx.ws.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

@Entity
@Table(name = "FILE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File implements OneOfFile{

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@ApiModelProperty(example = "cb61913d-d44f-4378-9488-5bcda7e2dbbb")
	@Column(name = "FILE_ID")
	private UUID fileId;
	@ApiModelProperty(example = "FilesBoxx - servisi.xlsx")
	@Column(name = "NAME")
	private String name;
	@Column(name = "DATA")
	@Lob
	private byte[] data;
	@ApiModelProperty(example = "false")
	@Column(name = "DELETED")
	private Boolean deleted;

	public File(UUID fileId, String name, byte[] data) {
		super();
		this.fileId = fileId;
		this.name = name;
		this.data = data;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
