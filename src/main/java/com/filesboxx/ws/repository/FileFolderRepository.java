package com.filesboxx.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.BelongsFileFolder;

@Repository
public interface FileFolderRepository extends JpaRepository<BelongsFileFolder, String>{

	BelongsFileFolder findByFileIdAndDeletedFalse(String fileId);
	
	List<BelongsFileFolder> findByFolderId(String folderId);

}
