package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.historiamedica.dto.EnfermedadActualDto;
import com.amachi.app.vitalia.historiamedica.entity.EnfermedadActual;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class EnfermedadActualMapperTest {
    private final EnfermedadActualMapper enfermedadActualMapper = Mappers.getMapper(EnfermedadActualMapper.class);

    @Test
    void shouldMapEnfermedadActualDtoToEntity() {
        // Given
        EnfermedadActualDto dto = Instancio.create(EnfermedadActualDto.class);

        // When
        EnfermedadActual entity = enfermedadActualMapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertNotNull(entity.getHistoriaMedica());
        assertEquals(dto.getHistoriaMedicaId(), entity.getHistoriaMedica().getId());
        assertEquals(dto.getNombre(), entity.getNombre());
        assertEquals(dto.getTratamientos(), entity.getTratamientos());
        assertEquals(dto.getFechaDiagnostico(), entity.getFechaDiagnostico());
        assertEquals(dto.getSintomas(), entity.getSintomas());

    }

    @Test
    void shouldMapEnfermedadActualToDto() {
        // Given
        EnfermedadActual entity = Instancio.create(EnfermedadActual.class);

        // When
        EnfermedadActualDto dto = enfermedadActualMapper.toDto(entity);

        // Then
        assertNotNull(dto);
        assertNotNull(dto.getHistoriaMedicaId());
        assertEquals(entity.getHistoriaMedica().getId(), dto.getHistoriaMedicaId());
        assertEquals(entity.getNombre(), dto.getNombre());
        assertEquals(entity.getTratamientos(), dto.getTratamientos());
        assertEquals(entity.getFechaDiagnostico(), dto.getFechaDiagnostico());
        assertEquals(entity.getSintomas(), dto.getSintomas());
        assertNotNull(dto.getHistoriaMedicaId());
    }
}