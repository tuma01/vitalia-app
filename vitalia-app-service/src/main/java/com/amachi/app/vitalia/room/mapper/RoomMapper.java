package com.amachi.app.vitalia.room.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.historiamedica.mapper.HospitalizacionMapper;
import com.amachi.app.vitalia.patient.entity.Patient;
import com.amachi.app.vitalia.room.dto.RoomDto;
import com.amachi.app.vitalia.room.entity.Room;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                HospitalizacionMapper.class
        }
)
public interface RoomMapper extends EntityDtoMapper<Room, RoomDto> {
    @Override
    @Mapping(target = "hospital.id", source = "hospitalId")
    @Mapping(target = "patients", source = "patientsIds", qualifiedByName = "patientIdsToPatientSet")
    @Mapping(target = "hospitalizaciones", source = "hospitalizacionesIds", qualifiedByName = "hospitalizacionesIdsToHospitalizacionSet")
    Room toEntity(RoomDto dto);

    @Override
    @Mapping(target = "hospitalId", source = "hospital.id")
    @Mapping(target = "patientsIds", source = "patients", qualifiedByName = "patientToPatientIdsSet")
    @Mapping(target = "hospitalizacionesIds", source = "hospitalizaciones", qualifiedByName = "hospitalizacionToHospitalizacionesIdsSet")
    RoomDto toDto(Room entity);

    // De Set<Long> -> Set<Patient>
    @Named("patientIdsToPatientSet")
    default Set<Patient> patientIdsToPatientSet(Set<Long> patientIds) {
        if (patientIds == null) {
            return null;
        }
        return patientIds.stream()
                .map(id -> Patient.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    // De Set<Patient> -> Set<Long>
    @Named("patientToPatientIdsSet")
    default Set<Long> patientToPatientIdsSet(Set<Patient> patients) {
        if (patients == null) {
            return null;
        }
        return patients.stream()
                .map(Patient::getId) // extraemos el ID
                .collect(Collectors.toSet());
    }
}
