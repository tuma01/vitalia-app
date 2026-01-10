package com.amachi.app.vitalia.management.tenantconfig.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.domain.tenant.mapper.TenantMapper;
import com.amachi.app.vitalia.management.tenantconfig.dto.TenantConfigDto;
import com.amachi.app.vitalia.management.tenantconfig.entity.TenantConfig;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true),
uses = {TenantMapper.class})
public interface TenantConfigMapper extends EntityDtoMapper<TenantConfig, TenantConfigDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "tenant.id", source = "tenantId")
    TenantConfig toEntity(TenantConfigDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tenant.id", source = "tenantId")
    void updateEntityFromDto(TenantConfigDto dto, @MappingTarget TenantConfig entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    @Mapping(target = "tenantId", source = "tenant.id")
    TenantConfigDto toDto(TenantConfig entity);
}