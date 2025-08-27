package com.amachi.app.vitalia.doctorprofessionspeciality.mapper;

import com.amachi.app.vitalia.doctorprofessionspeciality.dto.DoctorProfessionSpecialityDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.entity.DoctorProfessionSpeciality;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class DoctorProfessionSpecialityMapperTest {

    private final DoctorProfessionSpecialityMapper mapper = Mappers.getMapper(DoctorProfessionSpecialityMapper.class);

    @Test
    void shouldMapDoctorProfessionSpecialityDtoToEntity() {
        var dto = Instancio.create(DoctorProfessionSpecialityDto.class);
        var entity = mapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDescription(), entity.getDescription());

    }

    @Test
    void shouldMapDoctorProfessionSpecialityToDto() {
        var entity = Instancio.create(DoctorProfessionSpeciality.class);
        var dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
    }
}