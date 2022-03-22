package com.filesboxx.ws.controller.folder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderListDto {

    @ApiModelProperty(example = "2")
    Long count;
    List<FolderDto> items;

}
