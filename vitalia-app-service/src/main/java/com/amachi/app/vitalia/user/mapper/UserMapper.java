package com.amachi.app.vitalia.user.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.role.mapper.RoleMapper;
import com.amachi.app.vitalia.user.dto.UserDto;
import com.amachi.app.vitalia.user.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                RoleMapper.class
        }
)
public interface UserMapper extends EntityDtoMapper<User, UserDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "person", ignore = true) // 👈 lo resolvemos en el servicio
    User toEntity(UserDto dto);

    @Override
    @Mapping(target = "personId", source = "person.id")
    UserDto toDto(User entity);
}
