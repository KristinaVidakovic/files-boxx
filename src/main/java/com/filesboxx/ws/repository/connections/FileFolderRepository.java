package com.filesboxx.ws.repository.connections;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.connections.BelongsFileFolder;

@Repository
public interface FileFolderRepository extends JpaRepository<BelongsFileFolder, UUID>{

	BelongsFileFolder findByFileIdAndDeletedFalse(UUID fileId);
	
	List<BelongsFileFolder> findByFolderId(UUID folderId);

}
