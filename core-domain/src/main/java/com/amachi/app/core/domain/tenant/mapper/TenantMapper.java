package com.amachi.app.core.domain.tenant.mapper;

import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.domain.theme.entity.Theme;
import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.domain.tenant.dto.TenantDto;
import com.amachi.app.core.geography.address.dto.AddressDto;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface TenantMapper extends EntityDtoMapper<Tenant, TenantDto> {

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "theme", source = "themeId")
    @Mapping(target = "addressId", source = "address.id")
    Tenant toEntity(TenantDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "theme", source = "themeId")
    @Mapping(target = "addressId", source = "address.id")
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
