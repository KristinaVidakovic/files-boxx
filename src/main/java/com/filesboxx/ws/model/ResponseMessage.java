package com.filesboxx.ws.model;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiModelProperty;

public class ResponseMessage implements OneOfUser, OneOfFolder, OneOfFile{

	@ApiModelProperty(example = "Forwarded object doesn't exists.")
	private String message;
	@ApiModelProperty(example = "BAD_REQUEST/NO_CONTENT/OK")
	private HttpStatus status;
	
	public ResponseMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public ResponseMessage(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
	
	
}
