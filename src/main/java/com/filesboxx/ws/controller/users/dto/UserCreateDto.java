package com.filesboxx.ws.controller.users.dto;

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
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String email;

}
