package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.historiamedica.dto.RegistroEnfermedadDto;
import com.amachi.app.vitalia.historiamedica.entity.RegistroEnfermedad;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegistroEnfermedadMapperTest {

    private final RegistroEnfermedadMapper registroEnfermedadMapper = new RegistroEnfermedadMapperImpl();

    @Test
    void shouldMapRegistroEnfermedadDtoToEntity() {
        // Given
        RegistroEnfermedadDto dto = Instancio.create(RegistroEnfermedadDto.class);

        // When
        RegistroEnfermedad entity = registroEnfermedadMapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertNotNull(entity.getHistoriaMedica());
        assertEquals(dto.getHistoriaMedicaId(), entity.getHistoriaMedica().getId());
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getDescripcion(), entity.getDescripcion());
        assertEquals(dto.getNombreEnfermedad(), entity.getNombreEnfermedad());
        assertEquals(dto.getSintomas(), entity.getSintomas());
        assertEquals(dto.getTratamientos(), entity.getTratamientos());
    }

    @Test
    void shouldMapRegistroEnfermedadToDto() {
        // Given
        RegistroEnfermedad entity = Instancio.create(RegistroEnfermedad.class);

        // When
        RegistroEnfermedadDto dto = registroEnfermedadMapper.toDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getHistoriaMedica().getId(), dto.getHistoriaMedicaId());
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getDescripcion(), dto.getDescripcion());
        assertEquals(entity.getNombreEnfermedad(), dto.getNombreEnfermedad());
        assertEquals(entity.getSintomas(), dto.getSintomas());
        assertEquals(entity.getTratamientos(), dto.getTratamientos());
        assertNotNull(dto.getHistoriaMedicaId());
    }
}