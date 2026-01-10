package com.amachi.app.core.auth.mapper;

import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.common.dto.RoleDto;
import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper extends EntityDtoMapper<Role, RoleDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Role toEntity(RoleDto dto);

    @Override
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    RoleDto toDto(Role entity);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateEntityFromDto(RoleDto dto, @MappingTarget Role entity);
}
