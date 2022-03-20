package com.filesboxx.ws.controller.users;

import com.filesboxx.ws.controller.users.dto.UserCreateDto;
import com.filesboxx.ws.controller.users.dto.UserDto;
import com.filesboxx.ws.controller.users.dto.UserUpdateDto;
import com.filesboxx.ws.model.user.User;

import java.util.UUID;

public class UsersMapper {

    public static User toUser(UserCreateDto user) {
        return new User(UUID.randomUUID(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        null);
    }

    public static UserDto toUserDto(User saved) {
        return new UserDto(saved.getUserId(),
                            saved.getFirstName(),
                            saved.getLastName(),
                            saved.getUsername(),
                            saved.getPassword(),
                            saved.getEmail());
    }

    public static User merge(User user, UserUpdateDto dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return user;
    }

}
