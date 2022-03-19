package com.filesboxx.ws.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@Validated
@JsonSubTypes({
	@JsonSubTypes.Type(value = User.class),
	@JsonSubTypes.Type(value = ResponseMessage.class)
})
public interface OneOfUser{
}
