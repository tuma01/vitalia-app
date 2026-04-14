package com.amachi.app.vitalia.medical.history.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.history.dto.ObservationDto;
import com.amachi.app.vitalia.medical.history.entity.Observation;
import org.mapstruct.*;

/**
 * Mapper inteligente para observaciones y signos vitales (EHR Elite).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ObservationMapper extends EntityDtoMapper<Observation, ObservationDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "encounter.id", source = "encounterId")
    @Mapping(target = "doctor.id", source = "doctorId")
    Observation toEntity(ObservationDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientFullName", source = "patient.fullName")
    @Mapping(target = "encounterId", source = "encounter.id")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "doctorFullName", source = "doctor.fullName")
    @Mapping(target = "externalId", source = "externalId")
    ObservationDto toDto(Observation entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "encounter.id", source = "encounterId")
    @Mapping(target = "doctor.id", source = "doctorId")
    void updateEntityFromDto(ObservationDto dto, @MappingTarget Observation entity);
}
