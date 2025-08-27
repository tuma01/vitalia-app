package com.amachi.app.vitalia.departamento.mapper;

import com.amachi.app.vitalia.departamento.dto.DepartamentoDto;
import com.amachi.app.vitalia.departamento.entity.Departamento;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(InstancioExtension.class)
class DepartamentoMapperTest {

    private DepartamentoMapper departamentoMapper;

    @BeforeEach
    void setUp() {
        departamentoMapper = new DepartamentoMapperImpl();
    }

    @Test
    void shouldMapDepartamentoDtoToEntity() {
        DepartamentoDto dto = Instancio.create(DepartamentoDto.class);
        Departamento entity = departamentoMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getNombre(), entity.getNombre());
        assertEquals(dto.getPoblacion(), entity.getPoblacion());
        // Assuming countryId is mapped to country.id in the entity
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
        // Assuming country.id is mapped to countryId in the DTO
        assertNotNull(entity.getCountry());
        assertEquals(entity.getCountry().getId(), dto.getCountryId());
    }
}