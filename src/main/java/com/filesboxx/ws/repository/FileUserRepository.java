package com.filesboxx.ws.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.BelongsFileUser;

@Repository
public interface FileUserRepository extends JpaRepository<BelongsFileUser, UUID>{

	BelongsFileUser findByFileIdAndDeletedFalse(UUID fileId);

	List<BelongsFileUser> findByUserId(UUID userId);

}
