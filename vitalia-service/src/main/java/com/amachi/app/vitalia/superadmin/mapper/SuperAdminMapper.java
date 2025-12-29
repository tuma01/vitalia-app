package com.amachi.app.vitalia.superadmin.mapper;

import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.geography.address.mapper.AddressMapper;
import com.amachi.app.vitalia.person.mapper.PersonTenantMapper;
import com.amachi.app.vitalia.superadmin.dto.SuperAdminDto;
import com.amachi.app.vitalia.superadmin.entity.SuperAdmin;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true), uses = {
        AddressMapper.class,
        PersonTenantMapper.class
})
public interface SuperAdminMapper extends EntityDtoMapper<SuperAdmin, SuperAdminDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "address.id", source = "addressId") // Removed, handled in
    @Mapping(target = "personTenants", source = "personTenantsIds", qualifiedByName = "personTenantSetFromIdsForSuperAdmin")
    @Mapping(target = "user.id", source = "userId")
    SuperAdmin toEntity(SuperAdminDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address.id", source = "addressId") // Removed, handled in
    @Mapping(target = "personTenants", source = "personTenantsIds", qualifiedByName = "personTenantSetFromIdsForSuperAdmin")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "user.password", ignore = true) // Security Safeguard
    void updateEntityFromDto(SuperAdminDto dto, @MappingTarget SuperAdmin entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "addressId", source = "address.id")
    @Mapping(target = "personTenantsIds", source = "personTenants", qualifiedByName = "personTenantSetToIds")
    @Mapping(target = "userId", source = "user.id")
    SuperAdminDto toDto(SuperAdmin entity);
}
