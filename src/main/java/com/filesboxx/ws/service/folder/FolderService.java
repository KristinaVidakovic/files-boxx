package com.filesboxx.ws.service.folder;

import java.util.UUID;

import com.filesboxx.ws.controller.folder.dto.FolderCreateDto;
import com.filesboxx.ws.controller.folder.dto.FolderDto;
import com.filesboxx.ws.controller.folder.dto.FolderListDto;
import com.filesboxx.ws.model.response.ResponseMessage;

public interface FolderService {

	FolderDto folder(FolderCreateDto folder, UUID userId);
	
	FolderListDto folders(UUID userId);

	ResponseMessage deleteFolder(UUID folderId);
}
