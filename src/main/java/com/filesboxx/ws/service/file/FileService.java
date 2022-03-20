package com.filesboxx.ws.service.file;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.Body;
import com.filesboxx.ws.model.OneOfFile;
import com.filesboxx.ws.model.ResponseMessage;

public interface FileService {

	OneOfFile file(MultipartFile file, UUID userId);
	
	OneOfFile fileFolder(MultipartFile file, UUID folderId);
	
	ResponseMessage updateLocation(Body request);
	
	List<OneOfFile> files(UUID userId);
	
	List<OneOfFile> filesFolder(UUID folderId);

	ResponseMessage deleteFile(UUID fileId);

}
