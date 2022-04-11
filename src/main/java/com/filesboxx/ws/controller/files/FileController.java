package com.filesboxx.ws.controller.files;

import java.util.UUID;

import com.filesboxx.ws.controller.files.dto.*;
import com.filesboxx.ws.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.response.ResponseMessage;
import com.filesboxx.ws.service.file.FileService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/files")
public class FileController {

	private final FileService fileService;

	@Autowired
	FileController(FileService fileService){
		this.fileService = fileService;
	}
	
	@ApiOperation(value = "Method for inserting new file, outside the folder, by user ID.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = FileDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidUserException.class),
			@ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR", response = InvalidDataException.class)
	})
	@PostMapping(value = "/{userId}")
	public ResponseEntity save(
			@ApiParam(value = "Represents the file which should be inserted.", required = true) @RequestBody MultipartFile file, 
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable UUID userId){

		try {
			FileDto responseFile = fileService.save(file, userId);
			return new ResponseEntity(responseFile, HttpStatus.CREATED);
		} catch (InvalidUserException exception) {
			return new ResponseEntity(exception, InvalidUserException.HTTP_STATUS);
		} catch (InvalidDataException exception) {
			return new ResponseEntity(exception, InvalidDataException.HTTP_STATUS);
		}

	}
	
	@ApiOperation(value = "Method for inserting new file, inside the folder, by folder ID.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = FileDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidFolderException.class),
			@ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR", response = InvalidDataException.class)
	})
	@PostMapping(value = "/folder/{folderId}")
	public ResponseEntity saveFile(
			@ApiParam(value = "Represents the file which should be inserted.", required = true) @RequestBody MultipartFile file, 
			@ApiParam(value = "Value representing the unique folder identificator.", required = true) @PathVariable UUID folderId){

		try {
			FileDto responseFile = fileService.saveFile(file, folderId);
			return new ResponseEntity(responseFile, HttpStatus.CREATED);
		} catch (InvalidFolderException exception) {
			return new ResponseEntity(exception, InvalidFolderException.HTTP_STATUS);
		} catch (InvalidDataException exception) {
			return new ResponseEntity(exception, InvalidDataException.HTTP_STATUS);
		}

	}
	
	@ApiOperation(value = "Method for changing the location of the file.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = ResponseMessage.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidFileException.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidUserException.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidFolderException.class)
	})
	@PutMapping(value = "/change-location")
	public ResponseEntity updateLocation(
			@ApiParam(value = "JSON object representing change location.")
				@RequestBody FileLocationDto dto) {

		try {
			ResponseMessage message = fileService.updateLocation(dto);
			return new ResponseEntity(message, message.getStatus());
		} catch (InvalidFileException exception) {
			return new ResponseEntity(exception, InvalidFileException.HTTP_STATUS);
		} catch (InvalidUserException exception) {
			return new ResponseEntity(exception, InvalidUserException.HTTP_STATUS);
		} catch (InvalidFolderException exception) {
			return new ResponseEntity(exception, InvalidFolderException.HTTP_STATUS);
		}

	}
	
	@ApiOperation(value = "Method for getting files, outside the folders, by user ID.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = FileListDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidUserException.class)
	})
	@GetMapping(value = "/{userId}")
	public ResponseEntity list (
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable UUID userId) {

		try {
			FileListDto files = fileService.list(userId);
			return new ResponseEntity(files, HttpStatus.OK);
		} catch (InvalidUserException exception) {
			return new ResponseEntity(exception, InvalidUserException.HTTP_STATUS);
		}

	}
	
	@ApiOperation(value = "Method for getting files from specific folder.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = FileListDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidFolderException.class)
	})
	@GetMapping(value = "/folder/{folderId}")
	public ResponseEntity listFiles (
			@ApiParam(value = "Value representing the unique folder identificator.", required = true) @PathVariable UUID folderId) {

		try {
			FileListDto files = fileService.listFiles(folderId);
			return new ResponseEntity(files, HttpStatus.OK);
		} catch (InvalidFolderException exception) {
			return new ResponseEntity(exception, InvalidFolderException.HTTP_STATUS);
		}

	}

	@ApiOperation(value = "Method for deleting file by file ID.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = ResponseMessage.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST", response = InvalidFileException.class)
	})
	@DeleteMapping(value = "/{fileId}")
	public ResponseEntity delete(
			@ApiParam(value = "Value representing the unique file identificator.", required = true) @PathVariable UUID fileId) {

		try {
			ResponseMessage message = fileService.delete(fileId);
			return new ResponseEntity(message, message.getStatus());
		} catch (InvalidFileException exception) {
			return new ResponseEntity(exception, InvalidFileException.HTTP_STATUS);
		}

	}

}
