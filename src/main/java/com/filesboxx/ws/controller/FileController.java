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
import com.filesboxx.ws.model.OneOfFile;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.service.file.FileService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("files")
public class FileController {

	@Autowired
	private FileService fileService;
	
	@ApiOperation(value = "Method for inserting new file, outside the folder, by user ID.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = File.class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage.class),
					@ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR", response = ResponseMessage.class)})
	@RequestMapping(value = "/file/{userId}", method = RequestMethod.POST)
	public ResponseEntity<OneOfFile> file(
			@ApiParam(value = "Represents the file which should be inserted.", required = true) @RequestBody MultipartFile file, 
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable String userId){
		
		OneOfFile responseFile = fileService.file(file, userId);
		
		return new ResponseEntity<OneOfFile>(responseFile, responseFile instanceof File ? HttpStatus.OK : ((ResponseMessage)responseFile).getStatus());
	}
	
	@ApiOperation(value = "Method for inserting new file, inside the folder, by folder ID.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = File.class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage.class),
					@ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR", response = ResponseMessage.class)})
	@RequestMapping(value = "/file-folder/{folderId}", method = RequestMethod.POST)
	public ResponseEntity<OneOfFile> fileFolder(
			@ApiParam(value = "Represents the file which should be inserted.", required = true) @RequestBody MultipartFile file, 
			@ApiParam(value = "Value representing the unique folder identificator.", required = true) @PathVariable String folderId){
		
		OneOfFile responseFile = fileService.fileFolder(file, folderId);
		
		return new ResponseEntity<OneOfFile>(responseFile, responseFile instanceof File ? HttpStatus.OK : ((ResponseMessage)responseFile).getStatus());
	}
	
	@ApiOperation(value = "Method for change the location of the file.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = ResponseMessage.class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage.class)})
	@RequestMapping(value = "change-location", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateLocation(
			@ApiParam(value = "JSON object representing change location request.", required = true) @RequestBody Body request) {
		
		ResponseMessage message = fileService.updateLocation(request);
		
		return new ResponseEntity<ResponseMessage>(message, message.getStatus());
	}
	
	@ApiOperation(value = "Method for getting files, outside the folders, by user ID.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = File[].class),
					@ApiResponse(code = 204, message = "NO_CONTENT", response = ResponseMessage[].class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage[].class)})
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<OneOfFile>> files (
			@ApiParam(value = "Value representing the unique user identificator.", required = true) @PathVariable String userId) {
		
		List<OneOfFile> files = fileService.files(userId);
		
		return files.get(0) instanceof File ? new ResponseEntity<List<OneOfFile>>(files, HttpStatus.OK) 
				: new ResponseEntity<List<OneOfFile>>(files, ((ResponseMessage)files.get(0)).getStatus());
	}
	
	@ApiOperation(value = "Method for getting files from specific folder.")
	@ApiResponses({@ApiResponse(code = 200, message = "OK", response = File[].class),
					@ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseMessage[].class)})
	@RequestMapping(value = "files-folder/{folderId}", method = RequestMethod.GET)
	public ResponseEntity<List<OneOfFile>> filesFolder(
			@ApiParam(value = "Value representing the unique folder identificator.", required = true) @PathVariable String folderId) {
		
		List<OneOfFile> files = fileService.filesFolder(folderId);
		
		return new ResponseEntity<List<OneOfFile>>(files, files.get(0) instanceof File ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
