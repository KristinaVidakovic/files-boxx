package com.filesboxx.ws.service.user;

import com.filesboxx.ws.controller.users.dto.UserCreateDto;
import com.filesboxx.ws.controller.users.dto.UserDto;
import com.filesboxx.ws.controller.users.dto.UserSignInDto;
import com.filesboxx.ws.controller.users.dto.UserUpdateDto;
import com.filesboxx.ws.exceptions.*;
import com.filesboxx.ws.model.response.ResponseMessage;

import java.util.UUID;

public interface UserService {

	UserDto save(UserCreateDto user) throws InvalidAttributesException, UserExistsException;
	
	UserDto getUserByUserId(UUID userId) throws InvalidUserException;
	
	UserDto getUserSignIn(UserSignInDto body) throws InvalidAttributesException, InvalidUserException, InvalidPasswordException, UserSignInException;

    ResponseMessage signOut(UUID userId) throws UserSignOutException;

    UserDto update(UUID userId, UserUpdateDto dto) throws InvalidUserException;
}
