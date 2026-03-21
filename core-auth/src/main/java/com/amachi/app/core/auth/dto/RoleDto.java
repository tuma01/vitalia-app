package com.amachi.app.core.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Role", description = "Schema to hold Role information")
public class RoleDto {

    @Schema(description = "Identificador unico", example = "1")
    private Long id;

    @NotBlank(message = "Name of Role can not be empty")
    @Schema(description = "Name of Role", example = "ROLE_ADMIN")
    private String name;

    @Size(max = 255)
    @Schema(description = "Description of Role", example = "Administrator role with full access")
    private String description;
}
