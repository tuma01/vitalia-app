package com.amachi.app.vitalia.medicalcatalog.medication.mapper;

import com.amachi.app.vitalia.medicalcatalog.medication.dto.MedicationDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MedicationMapperTest {
    private MedicationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MedicationMapper.class);
    }

    @Test
    void shouldMapDtoToEntity() {
        MedicationDto dto = Instancio.create(MedicationDto.class);
        Medication entity = mapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getGenericName(), entity.getGenericName());
    }

    @Test
    void shouldMapEntityToDto() {
        Medication entity = Instancio.create(Medication.class);
        MedicationDto dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getGenericName(), dto.getGenericName());
    }
}
