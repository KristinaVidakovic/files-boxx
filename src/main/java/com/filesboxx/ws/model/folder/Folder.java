package com.filesboxx.ws.model.folder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "FOLDER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Folder {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@ApiModelProperty(example = "11e06451-5fb8-46c4-9050-5c5f605320c6")
	@Column(name = "FOLDER_ID")
	private UUID folderId;
	@ApiModelProperty(example = "New folder")
	@Column(name = "NAME")
	private String name;
	@ApiModelProperty(example = "false")
	@Column(name = "DELETED")
	private Boolean deleted;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
