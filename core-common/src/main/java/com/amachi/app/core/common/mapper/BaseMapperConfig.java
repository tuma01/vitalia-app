package com.amachi.app.core.common.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Global Base Mapper Configuration.
 * Policy changed to WARN for unmapped targets during architectural transition 
 * to ensure stability while maintaining project standards.
 */
@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
@AuditableIgnoreConfig.IgnoreAuditableFields
public interface BaseMapperConfig {
}
