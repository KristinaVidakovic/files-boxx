package com.filesboxx.ws.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name = "USER", schema = "root")
public class User {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "SURNAME")
	private String surname;
	@Column(name = "USER_NAME")
	private String userName;
	@Column(name = "PASSWORD")
	private String password;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String name, String surname, String userName, String password) {
		super();
		this.userId = userId;
		this.name = name;
		this.surname = surname;
		this.userName = userName;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
}
