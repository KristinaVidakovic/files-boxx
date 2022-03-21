package com.filesboxx.ws.service.file;

import java.util.Optional;
import java.util.UUID;

import com.filesboxx.ws.controller.files.dto.FileDto;
import com.filesboxx.ws.controller.files.dto.FileListDto;
import com.filesboxx.ws.controller.files.dto.FileLocationFolderDto;
import com.filesboxx.ws.controller.files.dto.FileLocationUserDto;
import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.ResponseMessage;

public interface FileService {

	FileDto file(MultipartFile file, UUID userId);
	
	FileDto fileFolder(MultipartFile file, UUID folderId);
	
	ResponseMessage updateLocation(Optional<FileLocationUserDto> locationUserDto,
								   Optional<FileLocationFolderDto> locationFolderDto);
	
	FileListDto files(UUID userId);
	
	FileListDto filesFolder(UUID folderId);

	ResponseMessage deleteFile(UUID fileId);

}
