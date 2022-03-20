package com.filesboxx.ws.controller.users.dto;

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

    @NotNull
    UUID id;
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
