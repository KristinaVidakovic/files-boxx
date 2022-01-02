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

import com.filesboxx.ws.model.Folder;
import com.filesboxx.ws.service.folder.FolderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "folders")
public class FolderController {
	
	@Autowired
	private FolderService folderService;
	
	@ApiOperation(value = "Method for inserting new folder, by user ID.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = Folder.class)})
	@RequestMapping(value = "folder/{userId}", method = RequestMethod.POST)
	public ResponseEntity<Folder> folder(
			@ApiParam(value = "JSON object representing the folder.", required = true) @RequestBody Folder folder, 
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable String userId){
		
		Folder responseFolder = folderService.folder(folder, userId);
		
		return new ResponseEntity<Folder>(responseFolder, responseFolder != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Method for getting list of folders by user ID.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = Folder[].class)})
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<Folder>> folders (
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable String userId) {
		
		List<Folder> folders = folderService.folders(userId);
		
		return folders == null ? new ResponseEntity<List<Folder>>(folders, HttpStatus.BAD_REQUEST) 
				: new ResponseEntity<List<Folder>>(folders, !folders.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}
}
