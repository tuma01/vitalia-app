package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Niveles de consumo de alcohol para evaluación clínica del estilo de vida.
 */
@Schema(description = "Niveles de consumo etílico")
public enum AlcoholConsumption {
    @Schema(description = "No consume alcohol")
    NONE,
    @Schema(description = "Consumo ocasional (eventual)")
    OCCASIONAL,
    @Schema(description = "Consumo regular (moderado)")
    REGULAR,
    @Schema(description = "Consumo excesivo (abusivo)")
    HEAVY
}
