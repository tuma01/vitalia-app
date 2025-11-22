package com.amachi.app.vitalia.avatar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Avatar", description = "Schema to hold Avatar information")
public class AvatarDto {

    @Schema(description = "Identificador único del avatar", example = "1")
    private Long id;

    @Schema(description = "Contenido del avatar en bytes. Representa la imagen codificada", example = "byte array")
    private byte[] content;

    @Schema(description = "Tipo MIME de la imagen, por ejemplo 'image/jpeg' o 'image/png'", example = "image/jpeg")
    private String mimeType;

    @Schema(description = "Nombre original del archivo subido", example = "profile.jpg")
    private String filename;

    @Schema(description = "Identificador del usuario al que pertenece el avatar", example = "42")
    private Long userId;

    @Schema(description = "Code del tenant al que pertenece el usuario", example = "HOS_JeanTalon")
    private String tenantCode;

    @Schema(description = "Tamaño del archivo en bytes", example = "102400")
    private Long size;
}
