package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoHospitalMapperImpl;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoTipoMapper;
import com.amachi.app.vitalia.historiamedica.mapper.HospitalizacionMapperImpl;
import com.amachi.app.vitalia.patient.dto.PatientVisitDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PatientVisitMapperTest {

    private PatientVisitMapper mapper;// = org.mapstruct.factory.Mappers.getMapper(PatientVisitMapper.class);

    @BeforeEach
    void setUp() {
        mapper = new PatientVisitMapperImpl(
                new HospitalizacionMapperImpl(
                    new DepartamentoHospitalMapperImpl(
                            Mappers.getMapper(DepartamentoTipoMapper.class)
                    )
                )
        );
    }

    @Test
    void shouldMapPatientVisitDtoToEntity() {
        // TODO add test
        PatientVisitDto dto = Instancio.create(PatientVisitDto.class);
        var entity = mapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getVisitDate(), entity.getVisitDate());
        assertEquals(dto.getVisitType(), entity.getVisitType());
        assertEquals(dto.getPatientId(), entity.getPatient().getId());
        assertEquals(dto.getHospitalId(), entity.getHospital().getId());
        assertEquals(dto.getHistoriaMedicaId(), entity.getHistoriaMedica().getId());
        assertEquals(dto.getHospitalizaciones().size(), entity.getHospitalizaciones().size());
    }

    @Test
    void shouldMapPatientVisitEntityToDto() {
        // TODO add test
        var entity = Instancio.create(com.amachi.app.vitalia.patient.entity.PatientVisit.class);
        var dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getVisitDate(), dto.getVisitDate());
        assertEquals(entity.getVisitType(), dto.getVisitType());
        assertEquals(entity.getPatient().getId(), dto.getPatientId());
        assertEquals(entity.getHospital().getId(), dto.getHospitalId());
        assertEquals(entity.getHistoriaMedica().getId(), dto.getHistoriaMedicaId());
        assertEquals(entity.getHospitalizaciones().size(), dto.getHospitalizaciones().size());
    }
}