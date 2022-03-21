package com.filesboxx.ws.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage implements OneOfFolder {

	@ApiModelProperty(example = "Forwarded object doesn't exists.")
	private String message;
	@ApiModelProperty(example = "BAD_REQUEST/NO_CONTENT/OK")
	private HttpStatus status;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
}
