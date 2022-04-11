package com.filesboxx.ws.controller.files.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileLocationDto {

    FileLocationFolderDto locationFolder;
    FileLocationUserDto locationUser;
}
