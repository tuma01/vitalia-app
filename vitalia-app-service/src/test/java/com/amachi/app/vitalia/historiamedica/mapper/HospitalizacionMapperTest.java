package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoHospitalMapperImpl;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoTipoMapper;
import com.amachi.app.vitalia.historiamedica.dto.HospitalizacionDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class HospitalizacionMapperTest {

    private HospitalizacionMapper hospitalizacionMapper;

    @BeforeEach
    void setUp() {
        hospitalizacionMapper = new HospitalizacionMapperImpl(
                new DepartamentoHospitalMapperImpl(Mappers.getMapper(DepartamentoTipoMapper.class))
        );
    }

    @Test
    void shouldMapHospitalizacionDtoToEntity() {
        var dto = Instancio.create(HospitalizacionDto.class);

        var entity = hospitalizacionMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getFechaIngreso(), entity.getFechaIngreso());
        assertEquals(dto.getFechaEgreso(), entity.getFechaEgreso());
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getMotivoIngreso(), entity.getMotivoIngreso());
        assertEquals(dto.getMotivoEgreso(), entity.getMotivoEgreso());
        assertEquals(dto.getDiagnostico(), entity.getDiagnostico());
        assertEquals(dto.getTratamiento(), entity.getTratamiento());
        assertEquals(dto.getObservaciones(), entity.getObservaciones());
        assertNotNull(entity.getRoom());
        assertEquals(dto.getRoomId(), entity.getRoom().getId());
        assertNotNull(entity.getPatientVisit());
        assertEquals(dto.getPatientVisitId(), entity.getPatientVisit().getId());
    }

    @Test
    void shouldMapHospitalizacionToDto() {
        var entity = Instancio.create(com.amachi.app.vitalia.historiamedica.entity.Hospitalizacion.class);
        var dto = hospitalizacionMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getFechaIngreso(), dto.getFechaIngreso());
        assertEquals(entity.getFechaEgreso(), dto.getFechaEgreso());
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getMotivoIngreso(), dto.getMotivoIngreso());
        assertEquals(entity.getMotivoEgreso(), dto.getMotivoEgreso());
        assertEquals(entity.getDiagnostico(), dto.getDiagnostico());
        assertEquals(entity.getTratamiento(), dto.getTratamiento());
        assertEquals(entity.getObservaciones(), dto.getObservaciones());
        assertNotNull(entity.getRoom());
        assertEquals(entity.getRoom().getId(), dto.getRoomId());
        assertNotNull(entity.getPatientVisit());
        assertEquals(entity.getPatientVisit().getId(), dto.getPatientVisitId());
    }
}