package com.amachi.app.vitalia.user.mapper;

import com.amachi.app.vitalia.role.mapper.RoleMapper;
import com.amachi.app.vitalia.user.dto.UserDto;
import com.amachi.app.vitalia.user.entity.User;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl(
                Mappers.getMapper(RoleMapper.class)
        );
    }

    @Test
    void shouldMapUserDtoToEntity() {
        UserDto dto = Instancio.create(UserDto.class);
        var entity = userMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getAvatar().length, entity.getAvatar().length);
        assertEquals(dto.getRoles().size(), entity.getRoles().size());
    }

    @Test
    void shouldMapUserToDto() {
        var entity = Instancio.create(User.class);
        var dto = userMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getPassword(), dto.getPassword());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getAvatar().length, dto.getAvatar().length);
        assertEquals(entity.getRoles().size(), dto.getRoles().size());
    }
}