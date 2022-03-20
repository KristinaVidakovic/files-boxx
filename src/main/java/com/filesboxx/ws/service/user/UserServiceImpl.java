package com.filesboxx.ws.service.user;

import com.filesboxx.ws.controller.users.UsersMapper;
import com.filesboxx.ws.controller.users.dto.UserCreateDto;
import com.filesboxx.ws.controller.users.dto.UserDto;
import com.filesboxx.ws.controller.users.dto.UserSignInDto;
import com.filesboxx.ws.controller.users.dto.UserUpdateDto;
import com.filesboxx.ws.exeptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.model.user.User;
import com.filesboxx.ws.repository.UserRepository;

import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
	static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepo;
	
	@Autowired
	UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	public UserDto create(UserCreateDto user) {
		
		log.info("Called POST method for registration new user.");
		
		if (user.getFirstName() == null || user.getLastName() == null ||
				user.getUsername() == null || user.getPassword() == null ||
				user.getEmail() == null) {
			log.error("All attributes must be forwarded.");
			throw new InvalidAttributesException();
		}
		
		User exist = userRepo.findByUsername(user.getUsername());
		
		if (exist != null) {
			log.error("User with forwarded username already exists.");
			throw new UserExistsException();
		}
		
		User saved = userRepo.save(UsersMapper.toUser(user));
		
		log.info("New user registered with username: " + user.getUsername() + "!");
		
		return UsersMapper.toUserDto(saved);
	}

	@Override
	public UserDto getUserByUserId(UUID userId) {
		
		log.info("Called GET method for getting user by user ID.");
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			throw new InvalidUserException();
		}
		
		User user = userRepo.findByUserId(userId);
		
		log.info("User found successfully!");
		
		return UsersMapper.toUserDto(user);
	}

	@Override
	public UserDto getUserSignIn(UserSignInDto dto) {

		log.info("Called method for signIn.");

		if (dto.getPassword() == null || dto.getUsername() == null) {
			log.error("All attributes must be forwarded.");
			throw new InvalidAttributesException();
		}

		User user = userRepo.findByUsername(dto.getUsername());

		if (user == null) {
			log.error("User with forwarded username doesn't exists.");
			throw new InvalidUserException();
		}

		if (!user.getPassword().equals(dto.getPassword())) {
			log.error("Wrong password!");
			throw new InvalidPasswordException();

		} else if (getTokens()) {
			log.error("Some user is already signed in.");
			throw new UserSignInException();

		} else {
			String token = Jwts
					.builder()
					.setId("softtekJWT")
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.compact();
			user.setToken(token);
			User saved = userRepo.save(user);
			log.info("User successfully signed in!");
			return UsersMapper.toUserDto(saved);
		}
	}

	@Override
	public ResponseMessage signOut() {
		log.info("Called method for signing out!");

		ResponseMessage message = new ResponseMessage();

		if (!getTokens()) {
			log.error("No user is signed in!");
			throw new UserSignOutException();

		} else {
			UUID userId = userRepo.findToken();
			User user = userRepo.findByUserId(userId);
			user.setToken(null);
			userRepo.save(user);

			log.info("User successfully signed out!");
			message.setMessage("User successfully signed out!");
			message.setStatus(HttpStatus.OK);
		}

		return message;

	}

	@Override
	public UserDto update(UUID userId, UserUpdateDto dto) {

		log.info("Called method for updating user.");

		User user = userRepo.findByUserId(userId);

		if (user == null) {
			log.error("Forwarded user doesn't exists.");
			throw new InvalidUserException();
		}

		User merged = UsersMapper.merge(user, dto);
		User saved = userRepo.save(merged);

		return UsersMapper.toUserDto(saved);
	}

	private boolean getTokens() {
		List<User> users = userRepo.findAll();
		return users.stream().anyMatch(i -> i.getToken() != null);
	}

}
