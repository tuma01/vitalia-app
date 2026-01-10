package com.amachi.app.core.auth.mapper;

import com.amachi.app.core.auth.dto.UserTenantRoleDto;
import com.amachi.app.core.auth.entity.UserTenantRole;
import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {
                UserMapper.class,
                RoleMapper.class
})
public interface UserTenantRoleMapper extends EntityDtoMapper<UserTenantRole, UserTenantRoleDto> {

        @Override
        @AuditableIgnoreConfig.IgnoreAuditableFields
        @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
        @Mapping(target = "tenant.id", source = "tenantId")
        UserTenantRole toEntity(UserTenantRoleDto dto);

        @Override
        @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
        @Mapping(target = "tenantId", source = "tenant.id")
        UserTenantRoleDto toDto(UserTenantRole entity);

        @AuditableIgnoreConfig.IgnoreAuditableFields
        @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
        @Mapping(target = "id", ignore = true)
        void updateEntityFromDto(UserTenantRoleDto dto, @MappingTarget UserTenantRole entity);
}
