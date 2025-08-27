package com.amachi.app.vitalia.departamentothospital.mapper;

import com.amachi.app.vitalia.departamentothospital.dto.DepartamentoHospitalDto;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoHospital;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartamentoHospitalMapperTest {

    private DepartamentoHospitalMapper departamentoHospitalMapper;

    @BeforeEach
    void setUp() {
        this.departamentoHospitalMapper = new DepartamentoHospitalMapperImpl(getMapper(DepartamentoTipoMapper.class));
    }

    @Test
    void shouldMapDepartamentoHospitalDtoToEntity() {
        DepartamentoHospitalDto dto = Instancio.create(DepartamentoHospitalDto.class);

        DepartamentoHospital entity = departamentoHospitalMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertNotNull(entity.getDepartamentoTipo());
        // Assuming the DepartamentoTipo is mapped correctly
        assertEquals(dto.getDepartamentoTipo().getName(), entity.getDepartamentoTipo().getName());
        assertEquals(dto.getDepartamentoTipo().getId(), entity.getDepartamentoTipo().getId());

        assertEquals(dto.getHeadDoctorId(), entity.getHeadDoctor().getId());
        assertEquals(dto.getHospitalId(), entity.getHospital().getId());
    }

    @Test
    void shouldMapDepartamentoHospitalToDto() {
        DepartamentoHospital entity = Instancio.create(DepartamentoHospital.class);

        DepartamentoHospitalDto dto = departamentoHospitalMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertNotNull(dto.getDepartamentoTipo());
        assertEquals(entity.getDepartamentoTipo().getName(), dto.getDepartamentoTipo().getName());
        assertEquals(entity.getDepartamentoTipo().getId(), dto.getDepartamentoTipo().getId());
        // Assuming the DepartamentoTipo is mapped correctly
        assertEquals(entity.getHeadDoctor().getId(), dto.getHeadDoctorId());
        assertEquals(entity.getHospital().getId(), dto.getHospitalId());
    }

    private <T> T getMapper(Class<T> mapperClass) {
        return Mappers.getMapper(mapperClass);
    }
}