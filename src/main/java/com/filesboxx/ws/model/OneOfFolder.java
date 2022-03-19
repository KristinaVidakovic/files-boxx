package com.filesboxx.ws.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@Validated
@JsonSubTypes({
	@JsonSubTypes.Type(value = Folder.class),
	@JsonSubTypes.Type(value = ResponseMessage.class)
})
public interface OneOfFolder {
}
