package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.historiamedica.dto.HistoriaMedicaDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class HistoriaMedicaMapperTest {

    private HistoriaMedicaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new HistoriaMedicaMapperImpl(
                new ConsultaMedicaMapperImpl(Mappers.getMapper(TipoConsultaMapper.class)),
                new EnfermedadActualMapperImpl(),
                new RegistroEnfermedadMapperImpl()
        );
    }

    @Test
    void shouldMapHistoriaMedicaDtoToEntity() {
        HistoriaMedicaDto dto = Instancio.create(HistoriaMedicaDto.class);
        var entity = mapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getAdmissionDate(), entity.getAdmissionDate());
        assertEquals(dto.getDate(), entity.getDate());
        assertEquals(dto.getObservations(), entity.getObservations());
        assertEquals(dto.getPatientId(), entity.getPatient().getId());
        assertEquals(dto.getDoctorId(), entity.getDoctor().getId());
        assertEquals(dto.getHospitalId(), entity.getHospital().getId());
    }

    @Test
    void shouldMapHistoriaMedicaEntityToDto() {
        var entity = Instancio.create(com.amachi.app.vitalia.historiamedica.entity.HistoriaMedica.class);
        var dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAdmissionDate(), dto.getAdmissionDate());
        assertEquals(entity.getDate(), dto.getDate());
        assertEquals(entity.getObservations(), dto.getObservations());
        assertEquals(entity.getPatient().getId(), dto.getPatientId());
        assertEquals(entity.getDoctor().getId(), dto.getDoctorId());
        assertEquals(entity.getHospital().getId(), dto.getHospitalId());
    }
}