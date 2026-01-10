package com.amachi.app.vitalia.management.theme.mapper;

import com.amachi.app.core.domain.theme.entity.Theme;
import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.domain.theme.dto.TenantThemeUpdateRequest;
import com.amachi.app.core.domain.theme.dto.ThemeDTO;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ThemeMapper extends EntityDtoMapper<Theme, ThemeDTO> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "tenant.id", source = "tenantId")
    Theme toEntity(ThemeDTO dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "tenantId", source = "tenant.id")
    ThemeDTO toDto(Theme entity);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "allowCustomCss", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "logoUrl", ignore = true)
    @Mapping(target = "faviconUrl", ignore = true)
    @Mapping(target = "customCss", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    void updateThemeFromRequest(TenantThemeUpdateRequest request, @MappingTarget Theme theme);
}
