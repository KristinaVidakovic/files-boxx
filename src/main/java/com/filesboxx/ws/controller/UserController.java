package com.filesboxx.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.filesboxx.ws.model.BodySignIn;
import com.filesboxx.ws.model.OneOfUser;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.model.User;
import com.filesboxx.ws.service.user.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class UserController {
	private final UserService userService;

	@Autowired
	UserController(UserService userService){
		this.userService = userService;
	}
	
	@ApiOperation(value = "Method for registration new user.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = User.class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage.class)})
	@RequestMapping(value = "auth/signup", method = RequestMethod.POST)
	public ResponseEntity<OneOfUser> user(
			@ApiParam(value = "A JSON object representing the user.", required = true) @RequestBody User user){
		
		OneOfUser responseUser = userService.user(user);
		
		return new ResponseEntity<>(responseUser, responseUser instanceof User ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Method for getting user by user ID.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = User.class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage.class)})
	@RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<OneOfUser> getUser (
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable String userId) {
		
		OneOfUser user = userService.getUserByUserId(userId);
		
		return new ResponseEntity<>(user, user instanceof User ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Method for Signing In user.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = User.class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage.class)})
	@RequestMapping(value = "auth/signin", method = RequestMethod.POST)
	public ResponseEntity<OneOfUser> getUser (
			@ApiParam(value = "Object representing the user's credentials.", required = true) @RequestBody BodySignIn body) {
		
		OneOfUser user = userService.getUserSignIn(body);
		
		return new ResponseEntity<>(user, user instanceof User ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Method for Signing Out user.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = User.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage.class)})
	@RequestMapping(value = "auth/signout", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> signOut () {

		ResponseMessage message = userService.signOut();

		return new ResponseEntity<>(message, message.getStatus());
	}
}
