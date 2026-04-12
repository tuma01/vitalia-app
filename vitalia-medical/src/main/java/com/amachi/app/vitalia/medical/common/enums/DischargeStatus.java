package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Catálogo de estados de egreso clínico para el cierre de episodios hospitalarios.
 */
@Schema(description = "Clasificación formal del alta o egreso hospitalario")
public enum DischargeStatus {
    
    @Schema(description = "Alta médica por curación o mejoría")
    RECOVERED,
    
    @Schema(description = "Egreso administrativo por solicitud del paciente o familiares")
    VOLUNTARY,
    
    @Schema(description = "Derivación técnica a otro centro de mayor complejidad")
    REFERRED,
    
    @Schema(description = "Fuga o egreso no autorizado del paciente")
    AWOL,
    
    @Schema(description = "Egreso formal por fallecimiento del paciente (Certificación)")
    DECEASED,
    
    @Schema(description = "Alta administrativa por fin de cobertura")
    ADMINISTRATIVE
}
