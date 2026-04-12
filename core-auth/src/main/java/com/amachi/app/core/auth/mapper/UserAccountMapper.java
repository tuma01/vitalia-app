package com.amachi.app.core.auth.mapper;

import com.amachi.app.core.auth.dto.UserAccountDto;
import com.amachi.app.core.auth.entity.UserAccount;
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
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    UserAccount toEntity(UserAccountDto dto);

    @Override
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    void updateEntityFromDto(UserAccountDto dto, @org.mapstruct.MappingTarget UserAccount entity);

    @Override
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "tenantName", source = "tenant.name")
    @Mapping(target = "tenantCode", source = "tenant.code")
    UserAccountDto toDto(UserAccount entity);
}
