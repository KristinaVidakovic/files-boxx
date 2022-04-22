package com.filesboxx.ws.controller.files.dto;

import io.swagger.annotations.ApiModelProperty;
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
public class FileDto {

    @ApiModelProperty(example = "cb61913d-d44f-4378-9488-5bcda7e2dbbb")
    UUID fileId;
    @ApiModelProperty(example = "FilesBoxx - servisi.xlsx")
    String name;
    @ApiModelProperty(example = "Forwarded data in bytes")
    byte[] data;

}