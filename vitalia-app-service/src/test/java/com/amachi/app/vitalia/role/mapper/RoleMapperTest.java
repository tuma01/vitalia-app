package com.amachi.app.vitalia.role.mapper;

import com.amachi.app.vitalia.role.dto.RoleDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleMapperTest {

    private final RoleMapper mapper = org.mapstruct.factory.Mappers.getMapper(RoleMapper.class);

    @Test
    void shouldMapRoleDtoToEntity() {
        RoleDto dto = Instancio.create(RoleDto.class);
        var entity = mapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
    }

    @Test
    void shouldMapRoleEntityToDto() {
        var entity = Instancio.create(com.amachi.app.vitalia.role.entity.Role.class);
        var dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }
}