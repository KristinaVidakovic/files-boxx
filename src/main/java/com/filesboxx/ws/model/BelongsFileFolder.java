package com.filesboxx.ws.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name = "BELONGS_FILE_FOLDER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
	
}
