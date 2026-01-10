package com.amachi.app.core.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Permission", description = "Schema to hold Permission information")
public class PermissionDto {

    @Schema(description = "Unique identifier of the permission", example = "1")
    private Long id;

    @Schema(description = "Name of the permission", example = "READ_PRIVILEGES")
    private String name;

    @Schema(description = "Description of the permission", example = "Allows reading of privileged data")
    private String description;
}
