package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.historiamedica.mapper.HospitalizacionMapper;
import com.amachi.app.vitalia.patient.dto.PatientVisitDto;
import com.amachi.app.vitalia.patient.entity.PatientVisit;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                HospitalizacionMapper.class
        }
)
public interface PatientVisitMapper extends EntityDtoMapper<PatientVisit, PatientVisitDto> {

    @Override
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "hospital.id", source = "hospitalId")
    @Mapping(target = "historiaMedica.id", source = "historiaMedicaId")
    PatientVisit toEntity(PatientVisitDto dto);

    @Override
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "hospitalId", source = "hospital.id")
    @Mapping(target = "historiaMedicaId", source = "historiaMedica.id")
    PatientVisitDto toDto(PatientVisit entity);
}
