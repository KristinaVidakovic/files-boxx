package com.filesboxx.ws.controller.files.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileLocationDto {

    FileLocationFolderDto locationFolder;
    FileLocationUserDto locationUser;
}
