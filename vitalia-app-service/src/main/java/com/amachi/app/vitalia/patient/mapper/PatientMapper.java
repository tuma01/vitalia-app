package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.address.mapper.AddressMapper;
import com.amachi.app.vitalia.common.mapper.*;
import com.amachi.app.vitalia.country.mapper.CountryMapper;
import com.amachi.app.vitalia.historiamedica.entity.Hospitalizacion;
import com.amachi.app.vitalia.hospital.mapper.HospitalMapperSupport;
import com.amachi.app.vitalia.nurse.mapper.NurseMapper;
import com.amachi.app.vitalia.patient.dto.PatientDto;
import com.amachi.app.vitalia.patient.entity.Patient;
import com.amachi.app.vitalia.patient.entity.PatientVisit;
import com.amachi.app.vitalia.room.mapper.RoomMapper;
import com.amachi.app.vitalia.user.mapper.UserMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        config = BaseMapperConfig.class,
        uses = {
                CountryMapper.class,
                RoomMapper.class,
                InsuranceMapper.class,
                NurseMapper.class,
                HabitoToxicoMapper.class,
                HabitoFisiologicoMapper.class,
                EmergencyContactMapper.class,
                AddressMapper.class,
                UserMapper.class,
                HospitalMapperSupport.class,
        },
        builder = @Builder(disableBuilder = true)
)
public interface PatientMapper extends EntityDtoMapper<Patient, PatientDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "historiaFamiliar.id", source = "historiaFamiliarId")
    @Mapping(target = "visits", source = "visitsIds", qualifiedByName = "patientVisitIdsToPatientVisitSet")
    @Mapping(target = "hospitalizaciones", source = "hospitalizacionesIds", qualifiedByName = "hospitalizacionesIdsToHospitalizacionSet")

    @Mapping(target = "hospitalPrincipal.id", source = "hospitalPrincipalId")
    @Mapping(target = "hospitales", source = "hospitalesIds" , qualifiedByName = "hospitalesIdsToHospitalSet")
    Patient toEntity(PatientDto dto);

    @Override
    @Mapping(target = "historiaFamiliarId", source = "historiaFamiliar.id")
    @Mapping(target = "visitsIds", source = "visits", qualifiedByName = "patientVisitToPatientVisitIdsSet")
    @Mapping(target = "hospitalizacionesIds", source = "hospitalizaciones", qualifiedByName = "hospitalizacionToHospitalizacionesIdsSet")
    @Mapping(target = "hospitalPrincipalId", source = "hospitalPrincipal.id")
    @Mapping(target = "hospitalesIds", source = "hospitales", qualifiedByName = "hospitalToHospitalesIdsSet")
    PatientDto toDto(Patient entity);

    // De Set<Long> -> Set<PatientVisit>
    @Named("patientVisitIdsToPatientVisitSet")
    default Set<PatientVisit> patientVisitIdsToPatientVisitSet(Set<Long> patientVisitIds) {
        if (patientVisitIds == null) {
            return null;
        }
        return patientVisitIds.stream()
                .map(id -> PatientVisit.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    // De Set<PatientVisit> -> Set<Long>
    @Named("patientVisitToPatientVisitIdsSet")
    default Set<Long> patientVisitToPatientVisitIdsSet(Set<PatientVisit> patientVisits) {
        if (patientVisits == null) {
            return null;
        }
        return patientVisits.stream()
                .map(PatientVisit::getId) // extraemos el ID
                .collect(Collectors.toSet());
    }

    // De Set<Long> -> Set<Hospitalizacion>
    @Named("hospitalizacionesIdsToHospitalizacionSet")
    default Set<Hospitalizacion> hospitalizacionesIdsToHospitalizacionSet(Set<Long> hospitalizacionesIds) {
        if (hospitalizacionesIds == null) {
            return null;
        }
        return hospitalizacionesIds.stream()
                .map(id -> Hospitalizacion.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    // De Set<Hospitalizacion> -> Set<Long>
    @Named("hospitalizacionToHospitalizacionesIdsSet")
    default Set<Long> hospitalizacionToHospitalizacionesIdsSet(Set<Hospitalizacion> hospitalizaciones) {
        if (hospitalizaciones == null) {
            return null;
        }
        return hospitalizaciones.stream()
                .map(Hospitalizacion::getId) // extraemos el ID
                .collect(Collectors.toSet());
    }
}
