package com.filesboxx.ws.repository.connection;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.connections.BelongsFolderUser;

@Repository
public interface FolderUserRepository extends JpaRepository<BelongsFolderUser, UUID> {

	List<BelongsFolderUser> findByUserUserIdAndDeletedFalse(UUID userId);

	BelongsFolderUser findByFolderFolderIdAndDeletedFalse(UUID folderId);
}
