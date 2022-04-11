package com.filesboxx.ws.model.connections;

import javax.persistence.*;

import com.filesboxx.ws.model.file.File;
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
	private UUID bfuId;
	@Column(name = "DELETED")
	private Boolean deleted;
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@OneToOne(targetEntity = File.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "FILE_ID")
	private File file;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
