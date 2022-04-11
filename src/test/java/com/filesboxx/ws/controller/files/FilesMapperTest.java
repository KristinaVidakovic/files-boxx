package com.filesboxx.ws.controller.files;

import com.filesboxx.ws.ObjectMother;
import com.filesboxx.ws.controller.files.dto.FileDto;
import com.filesboxx.ws.controller.files.dto.FileListDto;
import com.filesboxx.ws.model.file.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilesMapperTest {

    private static final UUID FILE_ID = UUID.fromString("7f5b571b-fb8c-4ac2-8224-0772ee29b0d7");

    @Test
    @DisplayName("Test toFileDto converts File to FileDto successfully")
    void toFileDto() {
        File file = ObjectMother.createFile();
        FileDto expected = new FileDto(
                FILE_ID,
                "File name",
                "Hello".getBytes());

        FileDto actual = FilesMapper.toFileDto(file);

        assertEquals(expected.getFileId(), actual.getFileId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getData()[0], actual.getData()[0]);
    }

    @Test
    @DisplayName("Test toFileListDto converts list of File object to FileListDto successfully")
    void toFileListDto() {
        List<File> files = new ArrayList<>();
        files.add(ObjectMother.createFile());
        List<FileDto> expected = new ArrayList<>();
        FileDto dto = new FileDto(FILE_ID,
                "File name",
                "Hello".getBytes());
        expected.add(dto);

        FileListDto actual = FilesMapper.toFileListDto(files);

        assertEquals(expected.size(), actual.getCount());
        assertEquals(expected.get(0).getFileId(), actual.getItems().get(0).getFileId());
        assertEquals(expected.get(0).getName(), actual.getItems().get(0).getName());
        assertEquals(expected.get(0).getData()[0], actual.getItems().get(0).getData()[0]);
    }
}