package com.amachi.app.vitalia.tenant.mapper;

import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.entity.Theme;
import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.tenant.dto.TenantDto;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface TenantMapper extends EntityDtoMapper<Tenant, TenantDto> {

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "theme", source = "themeId")
    @Mapping(target = "addressId", ignore = true)
    Tenant toEntity(TenantDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "theme", source = "themeId")
    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(TenantDto dto, @MappingTarget Tenant entity);

    default Theme mapTheme(Long themeId) {
        if (themeId == null) {
            return null;
        }
        return Theme.builder().id(themeId).build();
    }

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "themeId", source = "theme.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TenantDto toDto(Tenant entity);
}
