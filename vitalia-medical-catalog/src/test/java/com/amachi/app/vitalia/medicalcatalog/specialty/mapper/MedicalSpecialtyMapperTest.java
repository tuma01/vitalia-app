package com.amachi.app.vitalia.medicalcatalog.specialty.mapper;

import com.amachi.app.vitalia.medicalcatalog.specialty.dto.MedicalSpecialtyDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MedicalSpecialtyMapperTest {
    private MedicalSpecialtyMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MedicalSpecialtyMapper.class);
    }

    @Test
    void shouldMapDtoToEntity() {
        MedicalSpecialtyDto dto = Instancio.create(MedicalSpecialtyDto.class);
        MedicalSpecialty entity = mapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getTargetProfession(), entity.getTargetProfession());
    }

    @Test
    void shouldMapEntityToDto() {
        MedicalSpecialty entity = Instancio.create(MedicalSpecialty.class);
        MedicalSpecialtyDto dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getTargetProfession(), dto.getTargetProfession());
    }
}
