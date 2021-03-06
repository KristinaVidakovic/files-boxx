package com.filesboxx.ws.repository.connection;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.connections.BelongsFileFolder;

@Repository
public interface FileFolderRepository extends JpaRepository<BelongsFileFolder, UUID> {

	BelongsFileFolder findByFileFileIdAndDeletedFalse(UUID fileId);
	
	List<BelongsFileFolder> findByFolderFolderIdAndDeletedFalse(UUID folderId);
}
