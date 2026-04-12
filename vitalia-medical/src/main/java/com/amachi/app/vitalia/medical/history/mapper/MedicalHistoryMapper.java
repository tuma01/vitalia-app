package com.amachi.app.vitalia.medical.history.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.history.dto.MedicalHistoryDto;
import com.amachi.app.vitalia.medical.history.entity.MedicalHistory;
import org.mapstruct.*;

/**
 * Enterprise Mapper para el Expediente Clínico (SaaS Elite Tier).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface MedicalHistoryMapper extends EntityDtoMapper<MedicalHistory, MedicalHistoryDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "responsibleDoctor", ignore = true)
    @Mapping(target = "encounters", ignore = true)
    MedicalHistory toEntity(MedicalHistoryDto dto);

    @Override
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientFullName", source = "patient.fullName")
    @Mapping(target = "externalId", source = "externalId")
    MedicalHistoryDto toDto(MedicalHistory entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "responsibleDoctor", ignore = true)
    @Mapping(target = "encounters", ignore = true)
    void updateEntityFromDto(MedicalHistoryDto dto, @MappingTarget MedicalHistory entity);
}
