package com.filesboxx.ws.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "FILES_BOXX_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
}
