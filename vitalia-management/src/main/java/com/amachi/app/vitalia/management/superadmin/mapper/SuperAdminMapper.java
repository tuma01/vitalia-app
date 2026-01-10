package com.amachi.app.vitalia.management.superadmin.mapper;

import com.amachi.app.core.auth.mapper.UserMapper;
import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.geography.address.mapper.AddressMapper;
import com.amachi.app.core.domain.mapper.PersonTenantMapper;
import com.amachi.app.vitalia.management.superadmin.dto.SuperAdminDto;
import com.amachi.app.vitalia.management.superadmin.entity.SuperAdmin;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true), uses = {
        AddressMapper.class,
        PersonTenantMapper.class,
        UserMapper.class
})
public interface SuperAdminMapper extends EntityDtoMapper<SuperAdmin, SuperAdminDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "personTenants", source = "personTenantsIds", qualifiedByName = "personTenantSetFromIdsForSuperAdmin")
    SuperAdmin toEntity(SuperAdminDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "personTenants", source = "personTenantsIds", qualifiedByName = "personTenantSetFromIdsForSuperAdmin")
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(SuperAdminDto dto, @MappingTarget SuperAdmin entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "personTenantsIds", source = "personTenants", qualifiedByName = "personTenantSetToIds")
    SuperAdminDto toDto(SuperAdmin entity);
}
