package com.amachi.app.vitalia.medical.history.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.history.dto.EpisodeOfCareDto;
import com.amachi.app.vitalia.medical.history.entity.EpisodeOfCare;
import org.mapstruct.*;

/**
 * Mapper inteligente para la gestión de episodios de cuidados continuos (SaaS Elite Tier).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface EpisodeOfCareMapper extends EntityDtoMapper<EpisodeOfCare, EpisodeOfCareDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "managingPractitioner.id", source = "managingPractitionerId")
    EpisodeOfCare toEntity(EpisodeOfCareDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientFullName", source = "patient.fullName")
    @Mapping(target = "managingPractitionerId", source = "managingPractitioner.id")
    @Mapping(target = "managingPractitionerFullName", source = "managingPractitioner.fullName")
    @Mapping(target = "externalId", source = "externalId")
    EpisodeOfCareDto toDto(EpisodeOfCare entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "managingPractitioner.id", source = "managingPractitionerId")
    void updateEntityFromDto(EpisodeOfCareDto dto, @MappingTarget EpisodeOfCare entity);
}
