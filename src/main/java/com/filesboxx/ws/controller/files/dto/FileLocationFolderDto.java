package com.filesboxx.ws.controller.files.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileLocationFolderDto {

    @NotNull
    UUID fileId;
    @NotNull
    UUID folderId;
}
