package com.filesboxx.ws.service.file;

import java.util.UUID;

import com.filesboxx.ws.controller.files.dto.*;
import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.response.ResponseMessage;

public interface FileService {

	FileDto file(MultipartFile file, UUID userId);
	
	FileDto fileFolder(MultipartFile file, UUID folderId);
	
	ResponseMessage updateLocation(FileLocationDto dto);
	
	FileListDto files(UUID userId);
	
	FileListDto filesFolder(UUID folderId);

	ResponseMessage deleteFile(UUID fileId);
}
