package com.amachi.app.core.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Contextos funcionales de seguridad y navegación dentro de la plataforma.
 * Governs permissions and UI visibility.
 */
@Schema(description = "Contexto funcional de seguridad/acceso")
public enum RoleContext {
    @Schema(description = "Administrador Global de la Plataforma")
    SUPER_ADMIN,
    
    @Schema(description = "Administrador de Tenant (Hospital/Clínica)")
    ADMIN,
    
    @Schema(description = "Personal Médico Facultativo")
    DOCTOR,
    
    @Schema(description = "Paciente registrado")
    PATIENT,
    
    @Schema(description = "Personal de Enfermería")
    NURSE,
    
    @Schema(description = "Empleado administrativo genérico")
    EMPLOYEE,
    
    @Schema(description = "Recepcionista de centro")
    RECEPTIONIST,
    
    @Schema(description = "Técnico de Laboratorio")
    LAB_TECHNICIAN,
    
    @Schema(description = "Usuario Invitado / Sin perfil")
    GUEST
}
