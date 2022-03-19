package com.filesboxx.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, String>{

	Folder findByName (String name);

	Folder findByFolderId(String folderId);
	
	@Query("SELECT folderId FROM Folder WHERE folderId = ?1 AND deleted = FALSE")
	String folder (String folderId);

}
