package com.amachi.app.core.auth.mapper;

import com.amachi.app.core.auth.dto.UserAccountDto;
import com.amachi.app.core.auth.entity.UserAccount;
import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(config = BaseMapperConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserAccountMapper extends EntityDtoMapper<UserAccount, UserAccountDto> {

    @Override
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "tenantName", source = "tenant.name")
    UserAccountDto toDto(UserAccount entity);

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    UserAccount toEntity(UserAccountDto dto);
}
