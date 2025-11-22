package com.amachi.app.vitalia.avatar.mapper;

import com.amachi.app.vitalia.avatar.dto.AvatarDto;
import com.amachi.app.vitalia.avatar.entity.Avatar;
import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface AvatarMapper extends EntityDtoMapper<Avatar, AvatarDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    Avatar toEntity(AvatarDto dto);

    @Override
    AvatarDto toDto(Avatar entity);
}