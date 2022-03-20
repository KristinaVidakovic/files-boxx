package com.filesboxx.ws.service.user;

import com.filesboxx.ws.model.BodySignIn;
import com.filesboxx.ws.model.OneOfUser;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.model.User;

public interface UserService {

	OneOfUser user(User user);
	
	OneOfUser getUserByUserId(String userId);
	
	OneOfUser getUserSignIn(BodySignIn body);

    ResponseMessage signOut();
}
