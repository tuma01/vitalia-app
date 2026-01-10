package com.amachi.app.core.geography.departamento.mapper;

import com.amachi.app.core.geography.departamento.dto.DepartamentoDto;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class DepartamentoMapperTest {

    private DepartamentoMapper departamentoMapper;

    @BeforeEach
    void setUp() {
        departamentoMapper = Mappers.getMapper(DepartamentoMapper.class);
    }

    @Test
    void shouldMapDepartamentoDtoToEntity() {
        DepartamentoDto dto = Instancio.create(DepartamentoDto.class);
        Departamento entity = departamentoMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getNombre(), entity.getNombre());
        assertEquals(dto.getPoblacion(), entity.getPoblacion());
        assertNotNull(entity.getCountry());
        assertEquals(dto.getCountryId(), entity.getCountry().getId());
    }

    @Test
    void shouldMapDepartamentoToDto() {
        Departamento entity = Instancio.create(Departamento.class);
        DepartamentoDto dto = departamentoMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getNombre(), dto.getNombre());
        assertEquals(entity.getPoblacion(), dto.getPoblacion());
        assertNotNull(entity.getCountry());
        assertEquals(entity.getCountry().getId(), dto.getCountryId());
    }

}