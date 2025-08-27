package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.patient.dto.HabitoFisiologicoDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class HabitoFisiologicoMapperTest {

    private final HabitoFisiologicoMapper mapper = Mappers.getMapper(HabitoFisiologicoMapper.class);

    @Test
    void shouldMapHabitoFisiologicoDtoToEntity() {
        // Given
        var dto = Instancio.create(HabitoFisiologicoDto.class);
        // When
        var entity = mapper.toEntity(dto);
        // Then
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getActividadesDeportivas(), entity.getActividadesDeportivas());
        assertEquals(dto.getAlergias(), entity.getAlergias());
        assertEquals(dto.getDefecacion(), entity.getDefecacion());
        assertEquals(dto.getNutricion(), entity.getNutricion());
    }

    @Test
    void shouldMapHabitoFisiologicoEntityToDto() {
        // Given
        var entity = Instancio.create(com.amachi.app.vitalia.patient.entity.HabitoFisiologico.class);
        // When
        var dto = mapper.toDto(entity);
        // Then
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getActividadesDeportivas(), dto.getActividadesDeportivas());
        assertEquals(entity.getAlergias(), dto.getAlergias());
        assertEquals(entity.getDefecacion(), dto.getDefecacion());
        assertEquals(entity.getNutricion(), dto.getNutricion());
    }
}