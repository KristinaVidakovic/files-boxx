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
import com.filesboxx.ws.model.OneOfFolder;
import com.filesboxx.ws.model.ResponseMessage;
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
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = Folder.class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage.class)})
	@RequestMapping(value = "folder/{userId}", method = RequestMethod.POST)
	public ResponseEntity<OneOfFolder> folder(
			@ApiParam(value = "JSON object representing the folder.", required = true) @RequestBody Folder folder, 
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable String userId){
		
		OneOfFolder responseFolder = folderService.folder(folder, userId);
		
		return new ResponseEntity<OneOfFolder>(responseFolder, responseFolder instanceof Folder ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Method for getting list of folders by user ID.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = Folder[].class),
					@ApiResponse(code = 204, message = "NO_CONTENT", response = ResponseMessage[].class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage[].class)})
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<OneOfFolder>> folders (
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable String userId) {
		
		List<OneOfFolder> folders = folderService.folders(userId);
		
		return folders.get(0) instanceof Folder ? new ResponseEntity<List<OneOfFolder>>(folders, HttpStatus.OK) 
				: new ResponseEntity<List<OneOfFolder>>(folders, ((ResponseMessage)folders.get(0)).getStatus());
	}
}