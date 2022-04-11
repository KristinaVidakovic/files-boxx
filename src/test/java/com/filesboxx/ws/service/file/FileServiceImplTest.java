package com.filesboxx.ws.service.file;

import com.filesboxx.ws.ObjectMother;
import com.filesboxx.ws.controller.files.dto.FileDto;
import com.filesboxx.ws.controller.files.dto.FileLocationDto;
import com.filesboxx.ws.controller.files.dto.FileLocationFolderDto;
import com.filesboxx.ws.controller.files.dto.FileLocationUserDto;
import com.filesboxx.ws.exceptions.InvalidFolderException;
import com.filesboxx.ws.exceptions.InvalidUserException;
import com.filesboxx.ws.model.connections.BelongsFileFolder;
import com.filesboxx.ws.model.connections.BelongsFileUser;
import com.filesboxx.ws.model.file.File;
import com.filesboxx.ws.model.response.ResponseMessage;
import com.filesboxx.ws.model.user.User;
import com.filesboxx.ws.repository.connection.FileFolderRepository;
import com.filesboxx.ws.repository.connection.FileUserRepository;
import com.filesboxx.ws.repository.file.FileRepository;
import com.filesboxx.ws.repository.folder.FolderRepository;
import com.filesboxx.ws.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class FileServiceImplTest {

    @MockBean
    FileRepository fileRepository;
    @MockBean
    FileUserRepository fileUserRepository;
    @MockBean
    FileFolderRepository fileFolderRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    FolderRepository folderRepository;

    @Autowired
    FileServiceImpl fileService;

    @Test
    @DisplayName("Testing save -> saves File successfully.")
    void save() {
        MockMultipartFile file = ObjectMother.createMultipartFile();
        UUID userId = UUID.randomUUID();
        File saved = ObjectMother.createFile();

        doReturn(userId).when(userRepository).user(userId);
        doReturn(saved).when(fileRepository).save(Mockito.any(File.class));

        FileDto result = null;
        try {
            result = fileService.save(file, userId);
        } catch (InvalidUserException e) {
            e.printStackTrace();
        }

        assertEquals(saved.getName(), result.getName());
        assertEquals(saved.getData(), result.getData());
    }

    @Test
    @DisplayName("Testing save -> throws InvalidUserException.")
    void saveInvalidUser() {
        MockMultipartFile file = ObjectMother.createMultipartFile();
        UUID userId = UUID.randomUUID();
        InvalidUserException exception = new InvalidUserException();
        InvalidUserException actual = new InvalidUserException();

        doThrow(new InvalidUserException()).when(userRepository).user(userId);

        try {
            FileDto result = fileService.save(file, userId);
        } catch (InvalidUserException e) {
            actual = e;
        }

        assertEquals(exception.getMessage(), actual.getMessage());
        assertEquals(exception.getStatus(), actual.getStatus());
    }

    @Test
    @DisplayName("Testing saveFile -> saves File successfully.")
    void saveFile() {
        MockMultipartFile file = ObjectMother.createMultipartFile();
        UUID folderId = UUID.randomUUID();
        File saved = ObjectMother.createFile();

        doReturn(folderId).when(folderRepository).folder(folderId);
        doReturn(saved).when(fileRepository).save(Mockito.any(File.class));

        FileDto result = null;
        try {
            result = fileService.save(file, folderId);
        } catch (InvalidUserException e) {
            e.printStackTrace();
        }

        assertEquals(saved.getName(), result.getName());
        assertEquals(saved.getData(), result.getData());
    }

    @Test
    @DisplayName("Testing saveFile -> throws InvalidFolderException.")
    void saveFileInvalidFolder() {
        MockMultipartFile file = ObjectMother.createMultipartFile();
        UUID folderId = UUID.randomUUID();
        InvalidFolderException exception = new InvalidFolderException();
        InvalidFolderException actual = new InvalidFolderException();

        doThrow(new InvalidFolderException()).when(folderRepository).folder(folderId);

        try {
            FileDto result = fileService.save(file, folderId);
        } catch (InvalidFolderException e) {
            actual = e;
        }

        assertEquals(exception.getMessage(), actual.getMessage());
        assertEquals(exception.getStatus(), actual.getStatus());
    }

    @Test
    @DisplayName("Test updateLocation -> updates file location successfully.")
    void updateLocation() {
        UUID fileId = UUID.randomUUID();
        UUID folderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        FileLocationFolderDto locationFolderDto = new FileLocationFolderDto(fileId, folderId);
        FileLocationUserDto locationUserDto = new FileLocationUserDto(fileId, userId);
        FileLocationDto dto = new FileLocationDto(locationFolderDto, locationUserDto);
        File file = ObjectMother.createFile();
        User user = ObjectMother.createUser(Optional.empty());
        BelongsFileFolder bff = ObjectMother.createBelongsFileFolder(Optional.empty());
        Map bfuMap = new HashMap();
        bfuMap.put("user", user);
        bfuMap.put("file", file);
        BelongsFileUser bfu = ObjectMother.createBelongsFileUser(Optional.of(bfuMap));
        ResponseMessage message = new ResponseMessage("Successfully changed file location.", HttpStatus.OK);

        doReturn(fileId).when(fileRepository).file(locationFolderDto.getFileId());
        doReturn(userId).when(userRepository).user(locationUserDto.getUserId());
        doReturn(bff).when(fileFolderRepository).findByFileFileIdAndDeletedFalse(locationUserDto.getFileId());
        doReturn(file).when(fileRepository).findByFileId(locationUserDto.getFileId());
        doReturn(user).when(userRepository).findByUserId(locationUserDto.getUserId());
        doReturn(bfu).when(fileUserRepository).save(bfu);
        bff.setDeleted(true);
        doReturn(bff).when(fileFolderRepository).save(bff);

        ResponseMessage result = fileService.updateLocation(dto);

        assertEquals(message.getMessage(), result.getMessage());
        assertEquals(message.getStatus(), result.getStatus());
    }

    @Test
    void files() {
    }

    @Test
    void filesFolder() {
    }

    @Test
    void deleteFile() {
    }
}