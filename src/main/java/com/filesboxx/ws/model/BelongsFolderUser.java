package com.filesboxx.ws.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.filesboxx.ws.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

@Entity
@Table(name = "BELONGS_FOLDER_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BelongsFolderUser {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "BFU_ID")
	private UUID bfuId;
	@Column(name = "USER_ID")
	private UUID userId;
	@Column(name = "FOLDER_ID")
	private UUID folderId;
	@Column(name = "DELETED")
	private Boolean deleted;
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User user;
	
	@OneToOne(targetEntity = Folder.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "FOLDER_ID", referencedColumnName = "FOLDER_ID", insertable = false, updatable = false)
	private Folder folder;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
}
