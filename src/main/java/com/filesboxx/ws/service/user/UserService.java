package com.filesboxx.ws.service.user;

import com.filesboxx.ws.controller.users.dto.UserCreateDto;
import com.filesboxx.ws.controller.users.dto.UserDto;
import com.filesboxx.ws.controller.users.dto.UserSignInDto;
import com.filesboxx.ws.controller.users.dto.UserUpdateDto;
import com.filesboxx.ws.model.response.ResponseMessage;

import java.util.UUID;

public interface UserService {

	UserDto create(UserCreateDto user);
	
	UserDto getUserByUserId(UUID userId);
	
	UserDto getUserSignIn(UserSignInDto body);

    ResponseMessage signOut();

    UserDto update(UUID userId, UserUpdateDto dto);
}
