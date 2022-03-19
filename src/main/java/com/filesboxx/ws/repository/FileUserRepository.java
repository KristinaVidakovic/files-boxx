package com.filesboxx.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.BelongsFileUser;

@Repository
public interface FileUserRepository extends JpaRepository<BelongsFileUser, String>{

	BelongsFileUser findByFileIdAndDeletedFalse(String fileId);

	List<BelongsFileUser> findByUserId(String userId);

}
