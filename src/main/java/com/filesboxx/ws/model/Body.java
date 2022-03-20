package com.filesboxx.ws.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Body {

	@ApiModelProperty(example = "cb61913d-d44f-4378-9488-5bcda7e2dbbb")
	private UUID fileId;
	@ApiModelProperty(example = "11e06451-5fb8-46c4-9050-5c5f605320c6")
	private UUID folderId;
	@ApiModelProperty(example = "null")
	private UUID userId;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
	
}
