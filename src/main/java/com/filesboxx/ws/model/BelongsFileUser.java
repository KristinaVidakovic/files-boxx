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
@Table(name = "BELONGS_FILE_USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BelongsFileUser {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "BFU_ID")
	private String bfuId;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "FILE_ID")
	private String fileId;
	@Column(name = "DELETED")
	private Boolean deleted;
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User user;
	
	@OneToOne(targetEntity = File.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "FILE_ID", referencedColumnName = "FILE_ID", insertable = false, updatable = false)
	private File file;

	public BelongsFileUser(String bfuId, String userId, String fileId) {
		super();
		this.bfuId = bfuId;
		this.userId = userId;
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
}
