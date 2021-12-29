package com.filesboxx.ws.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.Body;
import com.filesboxx.ws.model.File;
import com.filesboxx.ws.model.Folder;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.model.User;

public interface FilesBoxxService {

	User user(User user);
	
	File file(MultipartFile file, String userId);
	
	File fileFolder(MultipartFile file, String folderId);
	
	Folder folder(Folder folder, String userId);
	
	ResponseMessage updateLocation(Body request);
	
	List<File> files(String userId);
	
	List<Folder> folders(String userId);
	
	List<File> filesFolder(String folderId);
}
