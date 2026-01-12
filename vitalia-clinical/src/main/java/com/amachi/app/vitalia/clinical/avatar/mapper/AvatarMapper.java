package com.amachi.app.vitalia.clinical.avatar.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.clinical.avatar.dto.AvatarDto;
import com.amachi.app.vitalia.clinical.avatar.entity.Avatar;
import org.mapstruct.*;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface AvatarMapper extends EntityDtoMapper<Avatar, AvatarDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "content", ignore = true)
    Avatar toEntity(AvatarDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "content", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AvatarDto dto, @MappingTarget Avatar entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    AvatarDto toDto(Avatar entity);
}
