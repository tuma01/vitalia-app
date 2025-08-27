package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoHospitalMapper;
import com.amachi.app.vitalia.historiamedica.dto.HospitalizacionDto;
import com.amachi.app.vitalia.historiamedica.entity.Hospitalizacion;
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
                DepartamentoHospitalMapper.class
        }
)
public interface HospitalizacionMapper extends EntityDtoMapper<Hospitalizacion, HospitalizacionDto> {

    @Override
    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "patientVisit.id", source = "patientVisitId")
    @Mapping(target = "patient.id", source = "patientId")
    Hospitalizacion toEntity(HospitalizacionDto dto);

    @Override
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "patientVisitId", source = "patientVisit.id")
    @Mapping(target = "patientId", source = "patient.id")
    HospitalizacionDto toDto(Hospitalizacion entity);

    @Named("hospitalizacionesIdsToHospitalizacionSet")
    default Set<Hospitalizacion> hospitalizacionesIdsToHospitalizacionSet(Set<Long> ids) {
        if (ids == null) return null;
        return ids.stream()
                .map(id -> Hospitalizacion.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    @Named("hospitalizacionToHospitalizacionesIdsSet")
    default Set<Long> hospitalizacionToHospitalizacionesIdsSet(Set<Hospitalizacion> entities) {
        if (entities == null) return null;
        return entities.stream().map(Hospitalizacion::getId).collect(Collectors.toSet());
    }
}
