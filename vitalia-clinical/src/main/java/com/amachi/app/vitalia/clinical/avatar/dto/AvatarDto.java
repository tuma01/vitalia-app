package com.amachi.app.vitalia.clinical.avatar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Avatar", description = "Schema to hold Avatar information")
public class AvatarDto {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @NotNull(message = "User ID shouldn't be null")
    @Schema(description = "ID del usuario", example = "1")
    private Long userId;

    @NotBlank(message = "Tenant code cannot be empty")
    @Schema(description = "Código del tenant", example = "GLOBAL")
    private String tenantCode;

    @Schema(description = "Nombre del archivo", example = "profile.png")
    private String filename;

    @Schema(description = "Tipo MIME del archivo", example = "image/png")
    private String mimeType;

    @Schema(description = "Tamaño del archivo en bytes", example = "1024")
    private Long size;
}
