package com.filesboxx.ws.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "FILES_BOXX_USER")
public class User implements OneOfUser{

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@ApiModelProperty(example = "8a19cfc7-13a9-44a3-8f44-43e93aa732d3")
	@Column(name = "USER_ID")
	private String userId;
	@ApiModelProperty(example = "Peric")
	@Column(name = "FIRST_NAME")
	private String firstName;
	@ApiModelProperty(example = "Pera")
	@Column(name = "LAST_NAME")
	private String lastName;
	@ApiModelProperty(example = "peraperic")
	@Column(name = "USERNAME")
	private String username;
	@ApiModelProperty(example = "pera123")
	@Column(name = "PASSWORD")
	private String password;
	@ApiModelProperty(example = "pera.peric@mail.com")
	@Email(regexp=".*@.*\\..*", message = "Email should be valid.")
	@Column(name = "EMAIL")
	private String email;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String userId, String firstName, String lastName, String username, String password, String email) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
}
