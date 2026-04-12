package com.amachi.app.vitalia.medical.infrastructure.dto;

import com.amachi.app.vitalia.medical.common.enums.TypeRoomEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Esquema Profesional Elite para la gestión de Habitaciones y Boxes Hospitalarios.
 * Estándar GOLD de Amachi Platform.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Room", description = "Habitación hospitalaria o Box de atención operativa")
public class RoomDto {

    @Schema(description = "Identificador único interno de la habitación", example = "701")
    private Long id;

    @Schema(description = "Identificador Global (FHIR/Publico)", example = "ROOM-304-PEDIATRIA")
    private String externalId;

    @NotNull(message = "Unidad {err.mandatory}")
    @Schema(description = "ID del pabellón o sección al que pertenece", example = "601")
    private Long unitId;

    @Schema(description = "Nombre oficial del pabellón (Solo lectura)", example = "ALA NORTE - PISO 3")
    private String unitName;

    @NotBlank(message = "Número Habitación {err.mandatory}")
    @Schema(description = "Identificador físico (Número o Nombre)", example = "304")
    private String roomNumber;

    @Schema(description = "Bloque o zona hospitalaria", example = "BLOQUE-A")
    private String blockCode;

    @Schema(description = "Piso funcional donde se ubica", example = "3")
    private Integer blockFloor;

    @Schema(description = "Categoría o nivel de confort", example = "ESTANDAR")
    private TypeRoomEnum typeRoom;

    @Schema(description = "Indica si la habitación es de uso privado individual", example = "true")
    private Boolean privateRoom;

    @Schema(description = "Estado actual de limpieza de la habitación", example = "CLEAN")
    private String cleaningStatus;

    @Schema(description = "Descripción adicional de la ubicación o equipamiento", example = "Cerca del ascensor de carga, equipada para traumatología")
    private String description;

    @Schema(description = "Indicador de operatividad en planta", example = "true")
    private Boolean active;
}
