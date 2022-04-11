package com.filesboxx.ws.service.file;

import java.util.UUID;

import com.filesboxx.ws.controller.files.dto.*;
import com.filesboxx.ws.exceptions.InvalidDataException;
import com.filesboxx.ws.exceptions.InvalidFileException;
import com.filesboxx.ws.exceptions.InvalidFolderException;
import com.filesboxx.ws.exceptions.InvalidUserException;
import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.response.ResponseMessage;

public interface FileService {

	FileDto save(MultipartFile file, UUID userId) throws InvalidUserException, InvalidDataException;
	
	FileDto saveFile(MultipartFile file, UUID folderId) throws InvalidFolderException, InvalidDataException;
	
	ResponseMessage updateLocation(FileLocationDto dto) throws InvalidFileException, InvalidUserException, InvalidFolderException;
	
	FileListDto list(UUID userId) throws InvalidUserException;
	
	FileListDto listFiles(UUID folderId) throws InvalidFolderException;

	ResponseMessage delete(UUID fileId) throws InvalidFileException;
}
