package com.amachi.app.vitalia.medical.history.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.history.dto.ActualIllnessDto;
import com.amachi.app.vitalia.medical.history.entity.ActualIllness;
import org.mapstruct.*;

/**
 * Enterprise Mapper para la gestión de enfermedades activas (SaaS Elite Tier).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ActualIllnessMapper extends EntityDtoMapper<ActualIllness, ActualIllnessDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @Mapping(target = "medicalHistory.id", source = "medicalHistoryId")
    @Mapping(target = "isDeleted", ignore = true)
    ActualIllness toEntity(ActualIllnessDto dto);

    @Override
    @Mapping(target = "medicalHistoryId", source = "medicalHistory.id")
    @Mapping(target = "externalId", source = "externalId")
    ActualIllnessDto toDto(ActualIllness entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "medicalHistory.id", source = "medicalHistoryId")
    @Mapping(target = "isDeleted", ignore = true)
    void updateEntityFromDto(ActualIllnessDto dto, @MappingTarget ActualIllness entity);
}
