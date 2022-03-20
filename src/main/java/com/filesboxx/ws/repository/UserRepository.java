package com.filesboxx.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByUsername(String username);
	
	User findByUserId(String userId);
	
	@Query("SELECT userId FROM User WHERE userId = ?1")
	String user(String userId);

	@Query("SELECT userId FROM User WHERE token is not null")
	String findToken();

}
