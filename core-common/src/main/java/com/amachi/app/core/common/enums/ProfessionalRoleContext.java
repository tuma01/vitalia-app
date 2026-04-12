package com.amachi.app.core.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Contextos funcionales de roles profesionales dentro de la plataforma.
 * Define la naturaleza de la actividad laboral.
 */
@Getter
@Schema(description = "Contexto operativo del rol profesional")
public enum ProfessionalRoleContext {
    @Schema(description = "Personal Médico Facultativo")
    MEDICAL("Médico"),
    
    @Schema(description = "Personal de Enfermería y Cuidados")
    NURSING("Enfermería"),
    
    @Schema(description = "Personal Administrativo y de Gestión")
    ADMINISTRATIVE("Administrativo"),
    
    @Schema(description = "Personal de Soporte Técnico u Operativo")
    SUPPORT("Soporte"),
    
    @Schema(description = "Personal de Servicios Generales")
    GENERAL_SERVICES("Servicios Generales"),
    
    @Schema(description = "Personal de Laboratorio y Diagnóstico")
    LABORATORY("Laboratorio"),
    
    @Schema(description = "Personal de Farmacia")
    PHARMACY("Farmacia"),
    
    @Schema(description = "Personal Directivo o Gerencial")
    MANAGEMENT("Gerencia");

    private final String label;

    ProfessionalRoleContext(String label) {
        this.label = label;
    }
}
