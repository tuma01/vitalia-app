package com.amachi.app.core.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Turnos de trabajo estandarizados para personal hospitalario.
 */
@Getter
@Schema(description = "Turnos operativos de trabajo")
public enum ShiftEnum {
    @Schema(description = "Turno matutino (ej: 07:00 - 15:00)")
    MORNING("Mañana"),
    
    @Schema(description = "Turno vespertino (ej: 14:00 - 22:00)")
    AFTERNOON("Tarde"),
    
    @Schema(description = "Turno nocturno (ej: 22:00 - 07:00)")
    NIGHT("Noche"),
    
    @Schema(description = "Turno de guardia 24 horas")
    ON_CALL("Guardia"),
    
    @Schema(description = "Horario administrativo regular")
    ADMINISTRATIVE("Administrativo");

    private final String label;

    ShiftEnum(String label) {
        this.label = label;
    }
}
