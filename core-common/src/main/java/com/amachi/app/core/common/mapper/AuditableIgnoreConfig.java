package com.amachi.app.core.common.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

/**
 * Global Configuration for MapStruct to ignore infrastructure fields.
 * This class defines custom annotations to be used in mappers to maintain a clean contract.
 */
@MapperConfig
public interface AuditableIgnoreConfig {
    /**
     * ✅ ÚSALA EN ENTIDADES GLOBALES (País, Provincia, CIE-10, etc.)
     * NO incluye 'tenantId' para evitar errores de compilación (Unknown Property).
     */
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @interface IgnoreAuditableFields {}
    /**
     * ✅ ÚSALA EN ENTIDADES ASOCIADAS A UN TENANT (Pacientes, Empleados, Citas, etc.)
     * INCLUYE 'tenantId' para asegurar el aislamiento por inquilino.
     */
    @Mapping(target = "tenantId", ignore = true) // <-- AQUÍ SÍ SE IGNORA EL AISLAMIENTO
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @interface IgnoreTenantAuditableFields {}
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @interface IgnoreSoftDelete {}
}
