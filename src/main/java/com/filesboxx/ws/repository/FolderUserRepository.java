package com.filesboxx.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.BelongsFolderUser;

@Repository
public interface FolderUserRepository extends JpaRepository<BelongsFolderUser, String>{

	List<BelongsFolderUser> findByUserId(String userId);

	BelongsFolderUser findByFolderId(String folderId);
}
