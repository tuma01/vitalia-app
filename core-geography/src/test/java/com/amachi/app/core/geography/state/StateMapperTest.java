package com.amachi.app.core.geography.state;

import com.amachi.app.core.geography.state.dto.StateDto;
import com.amachi.app.core.geography.state.entity.State;
import com.amachi.app.core.geography.state.mapper.StateMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class StateMapperTest {

    private StateMapper stateMapper;

    @BeforeEach
    void setUp() {
        stateMapper = Mappers.getMapper(StateMapper.class);
    }

    @Test
    void shouldMapStateDtoToEntity() {
        StateDto dto = Instancio.create(StateDto.class);
        State entity = stateMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getPopulation(), entity.getPopulation());
        assertNotNull(entity.getCountry());
        assertEquals(dto.getCountryId(), entity.getCountry().getId());
    }

    @Test
    void shouldMapStateToDto() {
        State entity = Instancio.create(State.class);
        StateDto dto = stateMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getPopulation(), dto.getPopulation());
        assertNotNull(entity.getCountry());
        assertEquals(entity.getCountry().getId(), dto.getCountryId());
    }

}
