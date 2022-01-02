package com.filesboxx.ws.service.file;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.Body;
import com.filesboxx.ws.model.File;
import com.filesboxx.ws.model.ResponseMessage;

public interface FileService {

	File file(MultipartFile file, String userId);
	
	File fileFolder(MultipartFile file, String folderId);
	
	ResponseMessage updateLocation(Body request);
	
	List<File> files(String userId);
	
	List<File> filesFolder(String folderId);
}
