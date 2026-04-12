package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Estados operativos del ciclo de vida del expediente médico.
 */
@Schema(description = "Estado administrativo de la historia clínica")
public enum MedicalHistoryStatus {
    
    @Schema(description = "Expediente activo y editable para atención clínica")
    ACTIVE,
    
    @Schema(description = "Expediente archivado por inactividad o traslado")
    ARCHIVED,
    
    @Schema(description = "Bloqueado por procesos legales o auditoría")
    LOCKED,
    
    @Schema(description = "Expediente pasivo por deceso del paciente")
    PASSIVE
}
