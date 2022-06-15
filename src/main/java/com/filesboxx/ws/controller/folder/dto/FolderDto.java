package com.filesboxx.ws.controller.folder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FolderDto {

    @ApiModelProperty(example = "515ab574-19f2-4609-907a-1bf7deb37e8a")
    UUID folderId;
    @ApiModelProperty(example = "New folder")
    String name;
    @ApiModelProperty(example = "2022-06-15T16:10:54.016+00:00")
    Date date;

}
