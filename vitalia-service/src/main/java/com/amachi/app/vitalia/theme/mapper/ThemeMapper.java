package com.amachi.app.vitalia.theme.mapper;

import com.amachi.app.vitalia.common.entity.Theme;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.theme.dto.TenantThemeUpdateRequest;
import com.amachi.app.vitalia.theme.dto.ThemeDTO;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ThemeMapper extends EntityDtoMapper<Theme, ThemeDTO> {

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    // Map other essential fields for creation if needed, or rely on
    // defaults/updates
    Theme toEntity(ThemeDTO dto);

    @Override
    ThemeDTO toDto(Theme entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "primaryColor", source = "primaryColor")
    @Mapping(target = "secondaryColor", source = "secondaryColor")
    @Mapping(target = "accentColor", source = "accentColor")
    @Mapping(target = "warnColor", source = "warnColor")
    @Mapping(target = "backgroundColor", source = "backgroundColor")
    @Mapping(target = "textColor", source = "textColor")
    @Mapping(target = "linkColor", source = "linkColor")
    @Mapping(target = "buttonTextColor", source = "buttonTextColor")
    @Mapping(target = "fontFamily", source = "fontFamily")
    @Mapping(target = "themeMode", source = "themeMode")
    @Mapping(target = "propertiesJson", source = "propertiesJson")
    void updateThemeFromRequest(TenantThemeUpdateRequest request, @MappingTarget Theme theme);
}
