package com.filesboxx.ws.controller.folder;

import java.util.UUID;

import com.filesboxx.ws.controller.folder.dto.FolderCreateDto;
import com.filesboxx.ws.controller.folder.dto.FolderDto;
import com.filesboxx.ws.controller.folder.dto.FolderListDto;
import com.filesboxx.ws.exceptions.FolderExistsException;
import com.filesboxx.ws.exceptions.InvalidArgumentException;
import com.filesboxx.ws.exceptions.InvalidFolderException;
import com.filesboxx.ws.exceptions.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.filesboxx.ws.model.response.ResponseMessage;
import com.filesboxx.ws.service.folder.FolderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/folders")
public class FolderController {

	private final FolderService folderService;

	@Autowired
	FolderController(FolderService folderService) {
		this.folderService = folderService;
	}
	
	@ApiOperation(value = "Method for inserting new folder, by user ID.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "CREATED", response = FolderDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidUserException.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = FolderExistsException.class)
	})
	@PostMapping(value = "/{userId}")
	public ResponseEntity create(
			@ApiParam(value = "JSON object representing the folder.", required = true) @RequestBody FolderCreateDto folder,
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable UUID userId){

		try {
			FolderDto responseFolder = folderService.folder(folder, userId);
			return new ResponseEntity(responseFolder, HttpStatus.CREATED);
		} catch (InvalidUserException exception) {
			return new ResponseEntity(exception, InvalidUserException.HTTP_STATUS);
		} catch (FolderExistsException exception) {
			return new ResponseEntity(exception, FolderExistsException.HTTP_STATUS);
		}

	}
	
	@ApiOperation(value = "Method for getting list of folders by user ID.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = FolderListDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidUserException.class)
	})
	@GetMapping(value = "/{userId}")
	public ResponseEntity folders (
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable UUID userId) {

		try {
			FolderListDto folders = folderService.folders(userId);
			return new ResponseEntity(folders, HttpStatus.OK);
		} catch (InvalidUserException exception) {
			return new ResponseEntity(exception, InvalidUserException.HTTP_STATUS);
		}

	}

	@ApiOperation(value = "Method for deleting folder by folder ID.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = ResponseMessage.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidFolderException.class)
	})
	@DeleteMapping(value = "/{folderId}")
	public ResponseEntity delete(
			@ApiParam(value = "Value representing the unique folder identificator.", required = true) @PathVariable UUID folderId) {

		try {
			ResponseMessage message = folderService.deleteFolder(folderId);
			return new ResponseEntity<>(message, message.getStatus());
		} catch (InvalidFolderException exception) {
			return new ResponseEntity(exception, InvalidFolderException.HTTP_STATUS);
		}

	}

}
