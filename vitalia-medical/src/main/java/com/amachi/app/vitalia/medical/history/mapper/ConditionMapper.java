package com.amachi.app.vitalia.medical.history.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.history.dto.ConditionDto;
import com.amachi.app.vitalia.medical.history.entity.Condition;
import org.mapstruct.*;

/**
 * Enterprise Mapper para diagnósticos y condiciones clínicas (SaaS Elite Tier).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ConditionMapper extends EntityDtoMapper<Condition, ConditionDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "encounter.id", source = "encounterId")
    @Mapping(target = "practitioner.id", source = "practitionerId")
    @Mapping(target = "medicalHistory.id", source = "medicalHistoryId")
    @Mapping(target = "icd10.id", source = "icd10Id")
    @Mapping(target = "episodeOfCare.id", source = "episodeOfCareId")
    @Mapping(target = "isDeleted", ignore = true)
    Condition toEntity(ConditionDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientFullName", source = "patient.fullName")
    @Mapping(target = "encounterId", source = "encounter.id")
    @Mapping(target = "practitionerId", source = "practitioner.id")
    @Mapping(target = "practitionerFullName", source = "practitioner.fullName")
    @Mapping(target = "medicalHistoryId", source = "medicalHistory.id")
    @Mapping(target = "icd10Id", source = "icd10.id")
    @Mapping(target = "icd10Code", source = "icd10.code")
    @Mapping(target = "episodeOfCareId", source = "episodeOfCare.id")
    @Mapping(target = "externalId", source = "externalId")
    ConditionDto toDto(Condition entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "encounter.id", source = "encounterId")
    @Mapping(target = "practitioner.id", source = "practitionerId")
    @Mapping(target = "medicalHistory.id", source = "medicalHistoryId")
    @Mapping(target = "icd10.id", source = "icd10Id")
    @Mapping(target = "episodeOfCare.id", source = "episodeOfCareId")
    @Mapping(target = "isDeleted", ignore = true)
    void updateEntityFromDto(ConditionDto dto, @MappingTarget Condition entity);
}
