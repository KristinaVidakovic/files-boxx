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
public class UserCreateDto {

    @NotNull
    @ApiModelProperty(example = "Pera")
    String firstName;
    @NotNull
    @ApiModelProperty(example = "Peric")
    String lastName;
    @NotNull
    @ApiModelProperty(example = "peraperic")
    String username;
    @NotNull
    @ApiModelProperty(example = "pera123")
    String password;
    @NotNull
    @ApiModelProperty(example = "pera.peric@mail.com")
    String email;

}
