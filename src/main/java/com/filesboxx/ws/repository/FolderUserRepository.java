package com.filesboxx.ws.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.BelongsFolderUser;

@Repository
public interface FolderUserRepository extends JpaRepository<BelongsFolderUser, UUID>{

	List<BelongsFolderUser> findByUserId(UUID userId);

	BelongsFolderUser findByFolderIdAndDeletedFalse(UUID folderId);

}
