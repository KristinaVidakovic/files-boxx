package com.filesboxx.ws.model;

import io.swagger.annotations.ApiModelProperty;

public class BodySignIn {

	@ApiModelProperty(example = "peraperic")
	private String username;
	@ApiModelProperty(example = "pera123")
	private String password;
	
	public BodySignIn() {
		super();
	}

	public BodySignIn(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	
	
}
