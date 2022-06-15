package com.filesboxx.ws.controller.files;

import com.filesboxx.ws.ObjectMother;
import com.filesboxx.ws.controller.files.dto.*;
import com.filesboxx.ws.exceptions.InvalidFileException;
import com.filesboxx.ws.exceptions.InvalidFolderException;
import com.filesboxx.ws.exceptions.InvalidUserException;
import com.filesboxx.ws.model.response.ResponseMessage;
import com.filesboxx.ws.service.file.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class FileControllerTest {

    @MockBean
    FileService fileService ;

    @Autowired
    FileController fileController;

    @Test
    @DisplayName("Testing save -> saved file for user successfully")
    void save() {
        MockMultipartFile file = ObjectMother.createMultipartFile();
        UUID userId = UUID.randomUUID();
        FileDto fileDto = new FileDto(
                UUID.randomUUID(),
                "File name",
                "txt",
                new Date(),
                "126KB",
                "Hello".getBytes());

        doReturn(fileDto).when(fileService).save(file, userId);

        ResponseEntity result = fileController.save(file, userId);

        assertEquals(fileDto, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing save -> throws InvalidUserException")
    void saveInvalidUser() {
        MockMultipartFile file = ObjectMother.createMultipartFile();
        UUID userId = UUID.randomUUID();
        InvalidUserException exception = new InvalidUserException();

        doThrow(new InvalidUserException()).when(fileService).save(file, userId);

        ResponseEntity result = fileController.save(file, userId);

        assertEquals(exception.getMessage(), ((InvalidUserException) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing saveFile -> saved file in folder successfully")
    void saveFile() {
        MockMultipartFile file = ObjectMother.createMultipartFile();
        UUID folderId = UUID.randomUUID();
        FileDto fileDto = new FileDto(
                UUID.randomUUID(),
                "File name",
                "txt",
                new Date(),
                "126KB",
                "Hello".getBytes());

        doReturn(fileDto).when(fileService).saveFile(file, folderId);

        ResponseEntity result = fileController.saveFile(file, folderId);

        assertEquals(fileDto, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing saveFile -> throws InvalidFolderException")
    void saveFileInvalidFolder() {
        MockMultipartFile file = ObjectMother.createMultipartFile();
        UUID folderId = UUID.randomUUID();
        InvalidFolderException exception = new InvalidFolderException();

        doThrow(new InvalidFolderException()).when(fileService).saveFile(file, folderId);

        ResponseEntity result = fileController.saveFile(file, folderId);

        assertEquals(exception.getMessage(), ((InvalidFolderException) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing updateLocation -> updated file location to folder successfully")
    void updateLocationFolder() {
        UUID fileId = UUID.randomUUID();
        UUID folderId = UUID.randomUUID();
        FileLocationFolderDto locationFolder = new FileLocationFolderDto(fileId, folderId);
        FileLocationUserDto locationUser = new FileLocationUserDto();
        FileLocationDto dto = new FileLocationDto(locationFolder, locationUser);
        ResponseMessage message = new ResponseMessage("Successfully changed file location.", HttpStatus.OK);

        doReturn(message).when(fileService).updateLocation(dto);

        ResponseEntity result = fileController.updateLocation(dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing updateLocation -> updated file location from folder successfully")
    void updateLocationUser() {
        UUID fileId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        FileLocationFolderDto locationFolder = new FileLocationFolderDto();
        FileLocationUserDto locationUser = new FileLocationUserDto(fileId, userId);
        FileLocationDto dto = new FileLocationDto(locationFolder, locationUser);
        ResponseMessage message = new ResponseMessage("Successfully changed file location.", HttpStatus.OK);

        doReturn(message).when(fileService).updateLocation(dto);

        ResponseEntity result = fileController.updateLocation(dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing updateLocation -> throws InvalidFileException")
    void updateLocationInvalidFile() {
        UUID fileId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID folderId = UUID.randomUUID();
        FileLocationFolderDto locationFolder = new FileLocationFolderDto(fileId, folderId);
        FileLocationUserDto locationUser = new FileLocationUserDto(fileId, userId);
        FileLocationDto dto = new FileLocationDto(locationFolder, locationUser);
        InvalidFileException exception = new InvalidFileException();

        doThrow(new InvalidFileException()).when(fileService).updateLocation(dto);

        ResponseEntity result = fileController.updateLocation(dto);

        assertEquals(exception.getMessage(), ((InvalidFileException) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing updateLocation -> throws InvalidUserException")
    void updateLocationInvalidUser() {
        UUID fileId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID folderId = UUID.randomUUID();
        FileLocationFolderDto locationFolder = new FileLocationFolderDto(fileId, folderId);
        FileLocationUserDto locationUser = new FileLocationUserDto(fileId, userId);
        FileLocationDto dto = new FileLocationDto(locationFolder, locationUser);
        InvalidUserException exception = new InvalidUserException();

        doThrow(new InvalidUserException()).when(fileService).updateLocation(dto);

        ResponseEntity result = fileController.updateLocation(dto);

        assertEquals(exception.getMessage(), ((InvalidUserException) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing updateLocation -> throws InvalidFolderException")
    void updateLocationInvalidFolder() {
        UUID fileId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID folderId = UUID.randomUUID();
        FileLocationFolderDto locationFolder = new FileLocationFolderDto(fileId, folderId);
        FileLocationUserDto locationUser = new FileLocationUserDto(fileId, userId);
        FileLocationDto dto = new FileLocationDto(locationFolder, locationUser);
        InvalidFolderException exception = new InvalidFolderException();

        doThrow(new InvalidFolderException()).when(fileService).updateLocation(dto);

        ResponseEntity result = fileController.updateLocation(dto);

        assertEquals(exception.getMessage(), ((InvalidFolderException) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing list -> returns FileListDto by userId successfully.")
    void list() {
        UUID userId = UUID.randomUUID();
        List<FileDto> files = new ArrayList<>();
        FileDto fileDto = new FileDto(
                UUID.randomUUID(),
                "File name",
                "txt",
                new Date(),
                "126KB",
                "Hello".getBytes());
        files.add(fileDto);
        FileListDto dto = new FileListDto(1L, files);

        doReturn(dto).when(fileService).list(userId);

        ResponseEntity result = fileController.list(userId);

        assertEquals(files, ((FileListDto) Objects.requireNonNull(result.getBody())).getItems());
        assertEquals(files.size(), ((FileListDto)result.getBody()).getCount());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing list -> throws InvalidUserException.")
    void listInvalidUser() {
        UUID userId = UUID.randomUUID();
        InvalidUserException exception = new InvalidUserException();

        doThrow(new InvalidUserException()).when(fileService).list(userId);

        ResponseEntity result = fileController.list(userId);

        assertEquals(exception.getMessage(), ((InvalidUserException) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing listFiles -> returns FileListDto by folderId successfully.")
    void listFiles() {
        UUID folderId = UUID.randomUUID();
        List<FileDto> files = new ArrayList<>();
        FileDto fileDto = new FileDto(
                UUID.randomUUID(),
                "File name",
                "txt",
                new Date(),
                "126KB",
                "Hello".getBytes());
        files.add(fileDto);
        FileListDto dto = new FileListDto(1L, files);

        doReturn(dto).when(fileService).listFiles(folderId);

        ResponseEntity result = fileController.listFiles(folderId);

        assertEquals(files, ((FileListDto) Objects.requireNonNull(result.getBody())).getItems());
        assertEquals(files.size(), ((FileListDto)result.getBody()).getCount());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing listFiles -> throws InvalidFolderException.")
    void listFilesInvalidFolder() {
        UUID folderId = UUID.randomUUID();
        InvalidFolderException exception = new InvalidFolderException();

        doThrow(new InvalidFolderException()).when(fileService).listFiles(folderId);

        ResponseEntity result = fileController.listFiles(folderId);

        assertEquals(exception.getMessage(), ((InvalidFolderException) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing delete -> deleted File by fileId successfully.")
    void delete() {
        UUID fileId = UUID.randomUUID();
        ResponseMessage message = new ResponseMessage("Successfully changed file location.", HttpStatus.OK);

        doReturn(message).when(fileService).delete(fileId);

        ResponseEntity result = fileController.delete(fileId);

        assertEquals(message.getMessage(), ((ResponseMessage) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Testing delete -> throws InvalidFileException.")
    void deleteInvalidFile() {
        UUID fileId = UUID.randomUUID();
        InvalidFileException exception = new InvalidFileException();

        doThrow(new InvalidFileException()).when(fileService).delete(fileId);

        ResponseEntity result = fileController.delete(fileId);

        assertEquals(exception.getMessage(), ((InvalidFileException) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}