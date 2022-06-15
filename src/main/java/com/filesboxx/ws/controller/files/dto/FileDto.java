package com.filesboxx.ws.controller.files.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    @ApiModelProperty(example = "cb61913d-d44f-4378-9488-5bcda7e2dbbb")
    UUID fileId;
    @ApiModelProperty(example = "FilesBoxx - servisi")
    String name;
    @ApiModelProperty(example = "xlsx")
    String extension;
    @ApiModelProperty(example = "2022-06-15T16:10:54.016+00:00")
    Date date;
    @ApiModelProperty(example = "0.15 MB")
    String size;
    @ApiModelProperty(example = "Forwarded data in bytes")
    byte[] data;

}
