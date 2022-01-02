package com.filesboxx.ws.service.folder;

import java.util.List;

import com.filesboxx.ws.model.Folder;

public interface FolderService {

	Folder folder(Folder folder, String userId);
	
	List<Folder> folders(String userId);
}
