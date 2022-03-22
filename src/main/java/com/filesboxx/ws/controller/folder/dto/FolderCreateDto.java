package com.filesboxx.ws.controller.folder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FolderCreateDto {

    @NotNull
    @ApiModelProperty(example = "New folder")
    String name;

}
