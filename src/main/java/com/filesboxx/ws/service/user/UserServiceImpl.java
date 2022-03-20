package com.filesboxx.ws.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.filesboxx.ws.model.BodySignIn;
import com.filesboxx.ws.model.OneOfUser;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.model.User;
import com.filesboxx.ws.repository.UserRepository;

import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepo;
	
	@Autowired
	UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	public OneOfUser user(User user) {
		
		log.info("Called POST method for registration new user.");
		
		ResponseMessage message = new ResponseMessage();
		
		if (user.getFirstName() == null || user.getLastName() == null || user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
			log.error("All attributes must be forwarded.");
			message.setMessage("All attributes must be forwarded.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}
		
		User exist = userRepo.findByUsername(user.getUsername());
		
		if (exist != null) {
			log.error("User with forwarded username already exists.");
			message.setMessage("User with forwarded username already exists.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}
		
		userRepo.save(user);
		
		log.info("New user registered with username: " + user.getUsername() + "!");
		
		return user;
	}

	@Override
	public OneOfUser getUserByUserId(String userId) {
		
		log.info("Called GET method for getting user by user ID.");
		
		ResponseMessage message = new ResponseMessage();
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			message.setMessage("Forwarded user doesn't exists.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}
		
		User user = userRepo.findByUserId(userId);
		
		log.info("User found successfully!");
		
		return user;
	}

	@Override
	public OneOfUser getUserSignIn(BodySignIn body) {

		log.info("Called method for signIn.");

		ResponseMessage message = new ResponseMessage();

		if (body.getPassword() == null || body.getUsername() == null) {
			log.error("Username and password should be forwarded.");
			message.setMessage("Username and password should be forwarded.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}

		User user = userRepo.findByUsername(body.getUsername());

		if (user == null) {
			log.error("User with forwarded username doesn't exists.");
			message.setMessage("User with forwarded username doesn't exists.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}

		if (!user.getPassword().equals(body.getPassword())) {
			log.error("Wrong password!");
			message.setMessage("Wrong password!");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;

		} else if (getTokens()) {
			String userId = userRepo.findToken();
			User signed = userRepo.findByUserId(userId);
			log.error("User "+ signed.getFirstName() + " " + signed.getLastName() + " is already signed in!");
			message.setMessage("User "+ signed.getFirstName() + " " + signed.getLastName() + " is already signed in!");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;

		} else {
			String token = Jwts
					.builder()
					.setId("softtekJWT")
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.compact();
			user.setToken(token);
			userRepo.save(user);
			log.info("User successfully signed in!");
			return user;
		}
	}

	@Override
	public ResponseMessage signOut() {
		log.info("Called method for signing out!");

		ResponseMessage message = new ResponseMessage();

		if (!getTokens()) {
			log.error("No user is signed in!");
			message.setMessage("No user is signed in!");
			message.setStatus(HttpStatus.BAD_REQUEST);

		} else {
			String userId = userRepo.findToken();
			User user = userRepo.findByUserId(userId);
			user.setToken(null);
			userRepo.save(user);

			log.info("User successfully signed out!");
			message.setMessage("User successfully signed out!");
			message.setStatus(HttpStatus.OK);
		}

		return message;

	}

	private boolean getTokens() {
		List<User> users = userRepo.findAll();
		return users.stream().anyMatch(i -> i.getToken() != null);
	}

}
