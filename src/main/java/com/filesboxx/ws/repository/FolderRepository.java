package com.filesboxx.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.Folder;

import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID>{

	Folder findByName (String name);

	Folder findByFolderId(UUID folderId);
	
	@Query("SELECT folderId FROM Folder WHERE folderId = ?1 AND deleted = FALSE")
	UUID folder (UUID folderId);

}
