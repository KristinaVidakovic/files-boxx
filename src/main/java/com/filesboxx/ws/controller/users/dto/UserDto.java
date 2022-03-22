package com.filesboxx.ws.controller.users.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @ApiModelProperty(example = "ded69329-2941-407e-ade7-6d0855eede05")
    UUID id;
    @ApiModelProperty(example = "Pera")
    String firstName;
    @ApiModelProperty(example = "Peric")
    String lastName;
    @ApiModelProperty(example = "peraperic")
    String username;
    @ApiModelProperty(example = "pera123")
    String password;
    @ApiModelProperty(example = "pera.peric@mail.com")
    String email;

}
