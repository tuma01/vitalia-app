package com.amachi.app.vitalia.avatar.mapper;

import com.amachi.app.vitalia.avatar.dto.AvatarDto;
import com.amachi.app.vitalia.avatar.entity.Avatar;
import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface AvatarMapper extends EntityDtoMapper<Avatar, AvatarDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    Avatar toEntity(AvatarDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    AvatarDto toDto(Avatar entity);
}