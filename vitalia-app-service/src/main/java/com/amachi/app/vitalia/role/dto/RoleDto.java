package com.amachi.app.vitalia.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Schema(name = "Role", description = "Schema to hold Role information")
public class RoleDto {

    @Schema(description = "Unique identifier of the room", example = "1")
    private Long id;

    @Schema(description = "Role name", example = "ADMIN")
    private String name;
}
