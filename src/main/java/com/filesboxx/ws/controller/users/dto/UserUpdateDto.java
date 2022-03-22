package com.filesboxx.ws.controller.users.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    @ApiModelProperty(example = "Pera")
    String firstName;
    @ApiModelProperty(example = "Peric")
    String lastName;
    @ApiModelProperty(example = "pera123")
    String password;
    @ApiModelProperty(example = "pera.peric@mail.com")
    String email;
}
