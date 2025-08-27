package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.patient.dto.HabitoToxicoDto;
import com.amachi.app.vitalia.patient.entity.HabitoToxico;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class HabitoToxicoMapperTest {
    private final HabitoToxicoMapper mapper = Mappers.getMapper(HabitoToxicoMapper.class);

    @Test
    void shouldMapHabitoToxicoDtoToEntity() {
        // Given
        var dto = Instancio.create(HabitoToxicoDto.class);
        // When
        var entity = mapper.toEntity(dto);
        // Then
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getAlcohol(), entity.getAlcohol());
        assertEquals(dto.getCafeina(), entity.getCafeina());
        assertEquals(dto.getDrogas(), entity.getDrogas());
        assertEquals(dto.getInfusiones(), entity.getInfusiones());

    }

    @Test
    void shouldMapHabitoToxicoEntityToDto() {
        // Given
        var entity = Instancio.create(HabitoToxico.class);
        // When
        var dto = mapper.toDto(entity);
        // Then
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAlcohol(), dto.getAlcohol());
        assertEquals(entity.getCafeina(), dto.getCafeina());
        assertEquals(entity.getDrogas(), dto.getDrogas());
        assertEquals(entity.getInfusiones(), dto.getInfusiones());
    }
}