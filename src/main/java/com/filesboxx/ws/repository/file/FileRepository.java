package com.filesboxx.ws.repository.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.file.File;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID>{

	File findByFileId(UUID fileId);
	
	@Query("SELECT fileId FROM File WHERE fileId = ?1 AND deleted = FALSE")
	UUID file (UUID fileId);

}
