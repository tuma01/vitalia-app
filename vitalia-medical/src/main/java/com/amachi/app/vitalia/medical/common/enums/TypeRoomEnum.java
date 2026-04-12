package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Clasificación premium de tipos de habitación disponibles.
 */
@Schema(description = "Tipos de habitación hospitalaria")
public enum TypeRoomEnum {
    @Schema(description = "Habitación compartida estándar")
    ESTANDAR,
    @Schema(description = "Habitación privada individual")
    PRIVADA,
    @Schema(description = "Suite médica de lujo con servicios extra")
    SUITE,
    @Schema(description = "Unidad de Cuidados Intensivos (UCI)")
    UCI,
    @Schema(description = "Unidad de Cuidados Intermedios")
    INTERMEDIA,
    @Schema(description = "Sala de recuperación post-anestésica")
    RECUPERACION
}
