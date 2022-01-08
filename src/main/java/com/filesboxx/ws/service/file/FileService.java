package com.filesboxx.ws.service.file;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.Body;
import com.filesboxx.ws.model.File;
import com.filesboxx.ws.model.OneOfFile;
import com.filesboxx.ws.model.ResponseMessage;

public interface FileService {

	OneOfFile file(MultipartFile file, String userId);
	
	OneOfFile fileFolder(MultipartFile file, String folderId);
	
	ResponseMessage updateLocation(Body request);
	
	List<OneOfFile> files(String userId);
	
	List<OneOfFile> filesFolder(String folderId);

	ResponseMessage deleteFile(String fileId);
}
