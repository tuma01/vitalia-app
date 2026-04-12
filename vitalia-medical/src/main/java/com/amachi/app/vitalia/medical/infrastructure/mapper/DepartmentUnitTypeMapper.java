package com.amachi.app.vitalia.medical.infrastructure.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.infrastructure.dto.DepartmentUnitTypeDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnitType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper para la conversion entre la entidad DepartmentUnitType y su DTO (SaaS Elite Tier).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface DepartmentUnitTypeMapper extends EntityDtoMapper<DepartmentUnitType, DepartmentUnitTypeDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    DepartmentUnitType toEntity(DepartmentUnitTypeDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    DepartmentUnitTypeDto toDto(DepartmentUnitType entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(DepartmentUnitTypeDto dto, @MappingTarget DepartmentUnitType entity);
}
