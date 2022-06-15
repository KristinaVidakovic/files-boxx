package com.filesboxx.ws.controller.files;

import com.filesboxx.ws.controller.files.dto.FileDto;
import com.filesboxx.ws.controller.files.dto.FileListDto;
import com.filesboxx.ws.model.file.File;

import java.util.ArrayList;
import java.util.List;

public class FilesMapper {

    public static FileDto toFileDto(File saved) {
        return new FileDto(
                saved.getFileId(),
                saved.getName(),
                saved.getExtension(),
                saved.getDate(),
                saved.getSize(),
                saved.getData());
    }

    public static FileListDto toFileListDto(List<File> list) {
        List<FileDto> items = new ArrayList<>();
        list.forEach(
                i -> items.add(toFileDto(i))
        );
        return new FileListDto((long) items.size(), items);
    }

}
