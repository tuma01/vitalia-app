package com.amachi.app.vitalia.role.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.role.dto.RoleDto;
import com.amachi.app.vitalia.role.entity.Role;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface RoleMapper extends EntityDtoMapper<Role, RoleDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    Role toEntity(RoleDto dto);

    @Override
    RoleDto toDto(Role entity);

}
