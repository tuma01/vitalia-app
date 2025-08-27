package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.patient.dto.EmergencyContactDto;
import com.amachi.app.vitalia.patient.entity.EmergencyContact;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class EmergencyContactMapperTest {
    private final EmergencyContactMapper mapper = Mappers.getMapper(EmergencyContactMapper.class);

    @Test
    void shouldMapEmergencyContactDtoToEntity() {
        // Given
        var dto = Instancio.create(EmergencyContactDto.class);

        // When
        var entity = mapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getPhone(), entity.getPhone());
    }

    @Test
    void shouldMapEmergencyContactEntityToDto() {
        // Given
        var entity = Instancio.create(EmergencyContact.class);

        // When
        var dto = mapper.toDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getPhone(), dto.getPhone());
    }
}