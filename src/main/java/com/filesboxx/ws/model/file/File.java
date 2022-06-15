package com.filesboxx.ws.model.file;

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

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "FILE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "FILE_ID")
	private UUID fileId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "EXTENSION")
	private String extension;
	@Column(name = "DATA")
	@Lob
	private byte[] data;
	@Column(name = "DATE")
	private Date date;
	@Column(name = "SIZE")
	private String size;
	@Column(name = "DELETED")
	private Boolean deleted;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
