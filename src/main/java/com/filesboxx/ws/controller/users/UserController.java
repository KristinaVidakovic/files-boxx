package com.filesboxx.ws.controller.users;

import com.filesboxx.ws.controller.users.dto.UserCreateDto;
import com.filesboxx.ws.controller.users.dto.UserDto;
import com.filesboxx.ws.controller.users.dto.UserUpdateDto;
import com.filesboxx.ws.controller.users.dto.UserSignInDto;
import com.filesboxx.ws.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.filesboxx.ws.model.response.ResponseMessage;
import com.filesboxx.ws.service.user.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	UserController(UserService userService){
		this.userService = userService;
	}
	
	@ApiOperation(value = "Method for registration new user.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "CREATED", response = UserDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidAttributesException.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = UserExistsException.class)
	})
	@PostMapping(value = "/auth/sign-up")
	public ResponseEntity create(
			@ApiParam(value = "A JSON object representing the user.", required = true) @RequestBody UserCreateDto user) {

		try{
			UserDto responseUser = userService.create(user);
			return new ResponseEntity(responseUser, HttpStatus.CREATED);
		} catch (InvalidAttributesException exception) {
			return new ResponseEntity(exception, InvalidAttributesException.HTTP_STATUS);
		} catch (UserExistsException exception) {
			return new ResponseEntity(exception, UserExistsException.HTTP_STATUS);
		}

	}
	
	@ApiOperation(value = "Method for getting user by user ID.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = UserDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidUserException.class)
	})
	@GetMapping(value = "/{userId}")
	public ResponseEntity findUser (
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable UUID userId) {

		try{
			UserDto user = userService.getUserByUserId(userId);
			return new ResponseEntity(user, HttpStatus.OK);
		} catch (InvalidUserException exception) {
			return new ResponseEntity(exception, InvalidUserException.HTTP_STATUS);
		}

	}
	
	@ApiOperation(value = "Method for signing in user.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = UserDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidAttributesException.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidUserException.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidPasswordException.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = UserSignInException.class)
	})
	@PostMapping(value = "/auth/sign-in")
	public ResponseEntity getUser (
			@ApiParam(value = "Object representing the user's credentials.", required = true) @RequestBody UserSignInDto dto) {

		try{
			UserDto user = userService.getUserSignIn(dto);
			return new ResponseEntity(user, HttpStatus.OK);
		} catch (InvalidAttributesException exception) {
			return new ResponseEntity(exception, InvalidAttributesException.HTTP_STATUS);
		} catch (InvalidUserException exception) {
			return new ResponseEntity(exception, InvalidUserException.HTTP_STATUS);
		} catch (InvalidPasswordException exception) {
			return new ResponseEntity(exception, InvalidPasswordException.HTTP_STATUS);
		} catch (UserSignInException exception) {
			return new ResponseEntity(exception, UserSignInException.HTTP_STATUS);
		}

	}

	@ApiOperation(value = "Method for signing out user.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = ResponseMessage.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage.class)
	})
	@PostMapping(value = "/auth/sign-out")
	public ResponseEntity signOut () {

		try {
			ResponseMessage message = userService.signOut();
			return new ResponseEntity<>(message, message.getStatus());
		} catch (UserSignOutException exception) {
			return new ResponseEntity(exception, UserSignOutException.HTTP_STATUS);
		}

	}

	@ApiOperation(value = "Method for updating user.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = UserDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidUserException.class)
	})
	@PutMapping("/{userId}")
	public ResponseEntity update (
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable UUID userId,
			@ApiParam(value = "Object representing user's new values.", required = true) @RequestBody UserUpdateDto dto) {

		try {
			UserDto user = userService.update(userId, dto);
			return new ResponseEntity(user, HttpStatus.OK);
		} catch (InvalidUserException exception) {
			return new ResponseEntity(exception, InvalidUserException.HTTP_STATUS);
		}

	}

}
