package com.amachi.app.vitalia.medicalcatalog.diagnosis.mapper;

import com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.Icd10Dto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Icd10MapperTest {
    private Icd10Mapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(Icd10Mapper.class);
    }

    @Test
    void shouldMapDtoToEntity() {
        Icd10Dto dto = Instancio.create(Icd10Dto.class);
        Icd10 entity = mapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getDescription(), entity.getDescription());
    }

    @Test
    void shouldMapEntityToDto() {
        Icd10 entity = Instancio.create(Icd10.class);
        Icd10Dto dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getDescription(), dto.getDescription());
    }
}
