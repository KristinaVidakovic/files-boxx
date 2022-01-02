package com.filesboxx.ws.service.user;

import com.filesboxx.ws.model.User;

public interface UserService {

	User user(User user);
	
	User getUser(String userId);
}
