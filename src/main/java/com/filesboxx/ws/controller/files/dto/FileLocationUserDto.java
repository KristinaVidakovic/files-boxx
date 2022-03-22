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
public class FileLocationUserDto {

    @NotNull
    @ApiModelProperty(example = "5283923c-a83b-42d1-bb6a-4b4a2088e16c")
    UUID fileId;
    @NotNull
    @ApiModelProperty(example = "ded69329-2941-407e-ade7-6d0855eede05")
    UUID userId;

}
