package com.filesboxx.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.filesboxx.ws.model.User;
import com.filesboxx.ws.service.user.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Method for registration new user.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = User.class)})
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<User> user(
			@ApiParam(value = "A JSON object representing the user.", required = true) @RequestBody User user){
		
		User responseUser = userService.user(user);
		
		return new ResponseEntity<User>(responseUser, responseUser != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
}
