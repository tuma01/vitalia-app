package com.amachi.app.vitalia.departamentothospital.mapper;

import com.amachi.app.vitalia.departamentothospital.dto.DepartamentoTipoDto;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoTipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartamentoTipoMapperTest {

    private DepartamentoTipoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DepartamentoTipoMapperImpl();
    }

    @Test
    void shouldMapDtoToEntity() {
        // Given a sample DTO
        DepartamentoTipoDto dto = DepartamentoTipoDto.builder()
                .id(1L)
                .name("Tipo de Departamento")
                .build();

        // When mapping to entity
        DepartamentoTipo entity = mapper.toEntity(dto);

        // Then the entity should not be null and fields should match
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
    }

    @Test
    void shouldMapDtoToDto() {
        // Given a sample entity
        DepartamentoTipo entity = DepartamentoTipo.builder()
                .id(2L)
                .name("Otro Tipo de Departamento")
                .build();

        // When mapping to DTO
        DepartamentoTipoDto dto = mapper.toDto(entity);

        // Then the DTO should not be null and fields should match
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }
}