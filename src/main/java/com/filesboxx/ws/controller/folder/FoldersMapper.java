package com.filesboxx.ws.controller.folder;

import com.filesboxx.ws.controller.folder.dto.FolderCreateDto;
import com.filesboxx.ws.controller.folder.dto.FolderDto;
import com.filesboxx.ws.controller.folder.dto.FolderListDto;
import com.filesboxx.ws.model.folder.Folder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FoldersMapper {

    public static Folder toFolder(FolderCreateDto dto) {
        return new Folder(
                UUID.randomUUID(),
                dto.getName(),
                false);
    }

    public static FolderDto toFolderDto(Folder saved) {
        return new FolderDto(saved.getFolderId(),
                saved.getName());
    }

    public static FolderListDto toFolderListDto(List<Folder> list) {
        List<FolderDto> folders = new ArrayList<>();
        list.forEach(
                i -> folders.add(toFolderDto(i))
        );
        return new FolderListDto((long) folders.size(), folders);
    }

}
