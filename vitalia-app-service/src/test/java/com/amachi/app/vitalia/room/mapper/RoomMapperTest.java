package com.amachi.app.vitalia.room.mapper;

import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoHospitalMapperImpl;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoTipoMapper;
import com.amachi.app.vitalia.historiamedica.mapper.HospitalizacionMapperImpl;
import com.amachi.app.vitalia.room.dto.RoomDto;
import com.amachi.app.vitalia.room.entity.Room;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class RoomMapperTest {
    private RoomMapper roomMapper;

    @BeforeEach
    void setUp() {
        roomMapper = new RoomMapperImpl(new HospitalizacionMapperImpl(
                new DepartamentoHospitalMapperImpl(Mappers.getMapper(DepartamentoTipoMapper.class))));
    }

    @Test
    void shouldMapRoomDtoToEntity() {
        RoomDto dto = Instancio.create(RoomDto.class);

        var entity = roomMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getNumberRoom(), entity.getNumberRoom());
        assertEquals(dto.getCapacity(), entity.getCapacity());
        assertEquals(dto.getBlockFloor(), entity.getBlockFloor());
        assertEquals(dto.getPrivateRoom(), entity.getPrivateRoom());

        assertNotNull(entity.getHospital());
        assertEquals(dto.getHospitalId(), entity.getHospital().getId());
        assertNotNull(entity.getPatients());
        assertEquals(dto.getPatientsIds().size(), entity.getPatients().size());
        assertNotNull(entity.getHospitalizaciones());
        assertEquals(dto.getHospitalizacionesIds().size(), entity.getHospitalizaciones().size());
    }

    @Test
    void shouldMapEntityToRoomDto() {
        var entity = Instancio.create(Room.class);
        var dto = roomMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getNumberRoom(), dto.getNumberRoom());
        assertEquals(entity.getCapacity(), dto.getCapacity());
        assertEquals(entity.getBlockFloor(), dto.getBlockFloor());
        assertEquals(entity.getPrivateRoom(), dto.getPrivateRoom());
        assertNotNull(dto.getHospitalId());
        assertEquals(entity.getHospital().getId(), dto.getHospitalId());
        assertNotNull(dto.getPatientsIds());
        assertEquals(entity.getPatients().size(), dto.getPatientsIds().size());
        assertNotNull(dto.getHospitalizacionesIds());
        assertEquals(entity.getHospitalizaciones().size(), dto.getHospitalizacionesIds().size());
    }
}