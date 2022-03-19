package com.filesboxx.ws.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BodySignIn {

	@ApiModelProperty(example = "peraperic")
	private String username;
	@ApiModelProperty(example = "pera123")
	private String password;

}
