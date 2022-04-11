package com.filesboxx.ws.model.connections;

import javax.persistence.*;

import com.filesboxx.ws.model.file.File;
import com.filesboxx.ws.model.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

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
	private UUID bffId;
	@Column(name = "DELETED")
	private Boolean deleted;
	
	@OneToOne(targetEntity = File.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "FILE_ID")
	private File file;
	
	@OneToOne(targetEntity = Folder.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "FOLDER_ID")
	private Folder folder;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
