package com.filesboxx.ws.service.folder;

import java.util.UUID;

import com.filesboxx.ws.controller.folder.dto.FolderCreateDto;
import com.filesboxx.ws.controller.folder.dto.FolderDto;
import com.filesboxx.ws.controller.folder.dto.FolderListDto;
import com.filesboxx.ws.exceptions.FolderExistsException;
import com.filesboxx.ws.exceptions.InvalidFolderException;
import com.filesboxx.ws.exceptions.InvalidUserException;
import com.filesboxx.ws.model.response.ResponseMessage;

public interface FolderService {

	FolderDto save(FolderCreateDto folder, UUID userId) throws InvalidUserException, FolderExistsException;
	
	FolderListDto list(UUID userId) throws InvalidUserException;

	ResponseMessage delete(UUID folderId) throws InvalidFolderException;
}
