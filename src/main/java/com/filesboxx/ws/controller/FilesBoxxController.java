package com.filesboxx.ws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.Body;
import com.filesboxx.ws.model.File;
import com.filesboxx.ws.model.Folder;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.model.User;
import com.filesboxx.ws.service.FilesBoxxService;

@RestController
public class FilesBoxxController {

	@Autowired
	private FilesBoxxService filesBoxxService;
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<User> user(@RequestBody User user){
		
		User responseUser = filesBoxxService.user(user);
		
		return new ResponseEntity<User>(responseUser, responseUser != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/file/{userId}", method = RequestMethod.POST)
	public ResponseEntity<File> file(@RequestBody MultipartFile file, @PathVariable String userId){
		
		File responseFile = filesBoxxService.file(file, userId);
		
		return new ResponseEntity<File>(responseFile, responseFile != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/file-folder/{folderId}", method = RequestMethod.POST)
	public ResponseEntity<File> fileFolder(@RequestBody MultipartFile file, @PathVariable String folderId){
		
		File responseFile = filesBoxxService.fileFolder(file, folderId);
		
		return new ResponseEntity<File>(responseFile, responseFile != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "folder/{userId}", method = RequestMethod.POST)
	public ResponseEntity<Folder> folder(@RequestBody Folder folder, @PathVariable String userId){
		
		Folder responseFolder = filesBoxxService.folder(folder, userId);
		
		return new ResponseEntity<Folder>(responseFolder, responseFolder != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "change-location", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateLocation(@RequestBody Body request) {
		
		ResponseMessage message = filesBoxxService.updateLocation(request);
		
		return new ResponseEntity<ResponseMessage>(message, message.getStatus());
	}
	
	@RequestMapping(value = "files/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<File>> files (@PathVariable String userId) {
		
		List<File> files = filesBoxxService.files(userId);
		
		return files == null ? new ResponseEntity<List<File>>(files, HttpStatus.BAD_REQUEST) 
				: new ResponseEntity<List<File>>(files, !files.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "folders/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<Folder>> folders (@PathVariable String userId) {
		
		List<Folder> folders = filesBoxxService.folders(userId);
		
		return folders == null ? new ResponseEntity<List<Folder>>(folders, HttpStatus.BAD_REQUEST) 
				: new ResponseEntity<List<Folder>>(folders, !folders.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "files-folder/{folderId}", method = RequestMethod.GET)
	public ResponseEntity<List<File>> filesFolder(@PathVariable String folderId) {
		
		List<File> files = filesBoxxService.filesFolder(folderId);
		
		return files == null ? new ResponseEntity<List<File>>(files, HttpStatus.BAD_REQUEST) 
				: new ResponseEntity<List<File>>(files, !files.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}
}
