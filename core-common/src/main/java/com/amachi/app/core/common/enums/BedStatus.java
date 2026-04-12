package com.amachi.app.core.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Estados maestros de disponibilidad y ocupación de camas hospitalarias.
 */
@Getter
@Schema(description = "Estados operativos de una cama")
public enum BedStatus {
    @Schema(description = "Cama libre y lista para asignación")
    AVAILABLE("Disponible"),
    
    @Schema(description = "Cama ocupada por un paciente (Hospitalización activa)")
    OCCUPIED("Ocupada"),
    
    @Schema(description = "Cama en proceso de desinfección o limpieza")
    CLEANING("Limpieza"),
    
    @Schema(description = "Cama fuera de servicio por falla técnica")
    MAINTENANCE("Mantenimiento"),
    
    @Schema(description = "Cama bloqueada administrativamente")
    RESERVED("Reservada");

    private final String label;

    BedStatus(String label) {
        this.label = label;
    }
}
