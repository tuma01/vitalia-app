package com.amachi.app.vitalia.medical.history.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.history.dto.MedicationRequestDto;
import com.amachi.app.vitalia.medical.history.entity.MedicationRequest;
import org.mapstruct.*;

/**
 * Mapper inteligente para la gestión de prescripciones y recetas digitales (SaaS Elite Tier).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface MedicationRequestMapper extends EntityDtoMapper<MedicationRequest, MedicationRequestDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patient.id",      source = "patientId")
    @Mapping(target = "encounter.id",    source = "encounterId")
    @Mapping(target = "practitioner.id", source = "practitionerId")
    @Mapping(target = "medication.id",   source = "medicationId")
    @Mapping(target = "medicationDisplayName", source = "medicationDisplayName")
    MedicationRequest toEntity(MedicationRequestDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patientId",            source = "patient.id")
    @Mapping(target = "patientFullName",      source = "patient.fullName")
    @Mapping(target = "encounterId",          source = "encounter.id")
    @Mapping(target = "practitionerId",       source = "practitioner.id")
    @Mapping(target = "practitionerFullName", source = "practitioner.fullName")
    @Mapping(target = "medicationId",         source = "medication.id")
    @Mapping(target = "medicationDisplayName", source = "medicationDisplayName")
    @Mapping(target = "externalId",           source = "externalId")
    MedicationRequestDto toDto(MedicationRequest entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patient.id",      source = "patientId")
    @Mapping(target = "encounter.id",    source = "encounterId")
    @Mapping(target = "practitioner.id", source = "practitionerId")
    @Mapping(target = "medication.id",   source = "medicationId")
    void updateEntityFromDto(MedicationRequestDto dto, @MappingTarget MedicationRequest entity);
}
