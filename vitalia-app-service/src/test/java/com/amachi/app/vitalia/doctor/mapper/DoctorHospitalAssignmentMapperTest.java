package com.amachi.app.vitalia.doctor.mapper;

import com.amachi.app.vitalia.doctor.dto.DoctorHospitalAssignmentDto;
import com.amachi.app.vitalia.doctor.entity.DoctorHospitalAssignment;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class DoctorHospitalAssignmentMapperTest {

    private final DoctorHospitalAssignmentMapper doctorHospitalAssignmentMapper = Mappers.getMapper(DoctorHospitalAssignmentMapper.class);

    @Test
    void shouldMapDoctorHospitalAssignmentDtoToEntity() {
        // Given
        DoctorHospitalAssignmentDto dto = Instancio.create(DoctorHospitalAssignmentDto.class);

        // When
        DoctorHospitalAssignment entity = doctorHospitalAssignmentMapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertNotNull(entity.getDoctor());
        assertNotNull(entity.getHospital());
        assertEquals(dto.getDoctorId(), entity.getDoctor().getId());
        assertEquals(dto.getHospitalId(), entity.getHospital().getId());
        assertEquals(dto.getStartDate(), entity.getStartDate());
        assertEquals(dto.getEndDate(), entity.getEndDate());
    }

    @Test
    void shouldMapDoctorHospitalAssignmentToDto() {
        // Given
        DoctorHospitalAssignment entity = Instancio.create(DoctorHospitalAssignment.class);

        // When
        DoctorHospitalAssignmentDto dto = doctorHospitalAssignmentMapper.toDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertNotNull(entity.getDoctor());
        assertNotNull(entity.getHospital());
        assertEquals(entity.getDoctor().getId(), dto.getDoctorId());
        assertEquals(entity.getHospital().getId(), dto.getHospitalId());
        assertEquals(entity.getStartDate(), dto.getStartDate());
        assertEquals(entity.getEndDate(), dto.getEndDate());
    }
}