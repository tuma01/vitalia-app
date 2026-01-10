package com.amachi.app.vitalia.medicalcatalog.procedure.mapper;

import com.amachi.app.vitalia.medicalcatalog.procedure.dto.MedicalProcedureDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MedicalProcedureMapperTest {
    private MedicalProcedureMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MedicalProcedureMapper.class);
    }

    @Test
    void shouldMapDtoToEntity() {
        MedicalProcedureDto dto = Instancio.create(MedicalProcedureDto.class);
        MedicalProcedure entity = mapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getName(), entity.getName());
    }

    @Test
    void shouldMapEntityToDto() {
        MedicalProcedure entity = Instancio.create(MedicalProcedure.class);
        MedicalProcedureDto dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getName(), dto.getName());
    }
}
