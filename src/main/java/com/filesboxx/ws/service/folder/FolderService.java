package com.filesboxx.ws.service.folder;

import java.util.List;
import java.util.UUID;

import com.filesboxx.ws.model.Folder;
import com.filesboxx.ws.model.OneOfFolder;
import com.filesboxx.ws.model.ResponseMessage;

public interface FolderService {

	OneOfFolder folder(Folder folder, UUID userId);
	
	List<OneOfFolder> folders(UUID userId);

	ResponseMessage deleteFolder(UUID folderId);

}
