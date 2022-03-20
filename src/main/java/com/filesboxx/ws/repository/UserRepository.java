package com.filesboxx.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.user.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	User findByUsername(String username);
	
	User findByUserId(UUID userId);
	
	@Query("SELECT userId FROM User WHERE userId = ?1")
	UUID user(UUID userId);

	@Query("SELECT userId FROM User WHERE token is not null")
	UUID findToken();

}
