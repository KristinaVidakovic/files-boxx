package com.filesboxx.ws.controller.users.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInDto {

    @NotNull
    @ApiModelProperty(example = "peraperic")
    String username;
    @NotNull
    @ApiModelProperty(example = "pera123")
    String password;

}
