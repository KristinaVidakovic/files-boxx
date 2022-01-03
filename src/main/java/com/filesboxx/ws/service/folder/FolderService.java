package com.filesboxx.ws.service.folder;

import java.util.List;

import com.filesboxx.ws.model.Folder;
import com.filesboxx.ws.model.OneOfFolder;

public interface FolderService {

	OneOfFolder folder(Folder folder, String userId);
	
	List<OneOfFolder> folders(String userId);
}
