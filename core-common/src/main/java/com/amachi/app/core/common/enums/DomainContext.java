package com.amachi.app.core.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Define la entidad de dominio clínico/operativo que debe crearse para un rol específico.
 * (SaaS Elite Tier - Context Separation)
 */
@Schema(description = "Contexto de entidad de dominio real")
public enum DomainContext {
    @Schema(description = "Entidad Hospitalaria (Paciente)")
    PATIENT,
    
    @Schema(description = "Entidad Facultativa (Médico)")
    DOCTOR,
    
    @Schema(description = "Entidad de Enfermería (Enfermero)")
    NURSE,
    
    @Schema(description = "Entidad Administrativa (Empleado)")
    EMPLOYEE,
    
    @Schema(description = "Entidad de Administración de Tenant")
    ADMIN,
    
    @Schema(description = "Entidad de Administración de Plataforma")
    SUPER_ADMIN
}
