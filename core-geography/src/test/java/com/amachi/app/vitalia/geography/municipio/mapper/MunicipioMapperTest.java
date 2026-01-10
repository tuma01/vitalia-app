package com.amachi.app.core.geography.municipio.mapper;

import com.amachi.app.core.geography.municipio.dto.MunicipioDto;
import com.amachi.app.core.geography.municipio.entity.Municipio;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class MunicipioMapperTest {

    private MunicipioMapper municipioMapper;

    @BeforeEach
    void setUp() {
        municipioMapper = Mappers.getMapper(MunicipioMapper.class);
    }

    @Test
    void shouldMapMunicipioDtoToEntity() {
        MunicipioDto dto = Instancio.create(MunicipioDto.class);
        Municipio entity = municipioMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getNombre(), entity.getNombre());
        assertEquals(dto.getPoblacion(), entity.getPoblacion());
        assertNotNull(entity.getProvincia());
        assertEquals(dto.getProvinciaId(), entity.getProvincia().getId());
    }

    @Test
    void shouldMapDepartamentoToDto() {
        Municipio entity = Instancio.create(Municipio.class);
        MunicipioDto dto = municipioMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getNombre(), dto.getNombre());
        assertEquals(entity.getPoblacion(), dto.getPoblacion());
        assertNotNull(entity.getProvincia());
        assertEquals(entity.getProvincia().getId(), dto.getProvinciaId());
    }
}