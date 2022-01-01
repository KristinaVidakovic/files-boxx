package com.filesboxx.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesboxx.ws.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, String>{

	File findByFileId(String fileId);
}
