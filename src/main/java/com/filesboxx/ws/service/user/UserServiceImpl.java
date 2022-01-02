package com.filesboxx.ws.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.filesboxx.ws.model.User;
import com.filesboxx.ws.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepo;
	
	public User user(User user) {
		
		log.info("Called POST method for registration new user.");
		
		if (user.getFirstName() == null || user.getLastName() == null || user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
			log.error("All attributes must be forwarded.");
			return null;
		}
		
		User exist = userRepo.findByUsername(user.getUsername());
		
		if (exist != null) {
			log.error("User with forwarded username already exists.");
			return null;
		}
		
		userRepo.save(user);
		
		log.info("New user registered: " + user.toString());
		
		return user;
	}

	@Override
	public User getUser(String userId) {
		
		log.info("Called GET method for getting user by user ID.");
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			return null;
		}
		
		User user = userRepo.findByUserId(userId);
		
		log.info("User : " + user.toString());
		
		return user;
	}


}
