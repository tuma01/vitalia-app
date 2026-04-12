package com.amachi.app.vitalia.medical.infrastructure.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.infrastructure.dto.BedDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Bed;
import org.mapstruct.*;

/**
 * Enterprise Mapper para gestión de camas hospitalarias (SaaS Elite Tier).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface BedMapper extends EntityDtoMapper<Bed, BedDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "hospitalization.id", source = "hospitalizationId")
    @Mapping(target = "isDeleted", ignore = true)
    Bed toEntity(BedDto dto);

    @Override
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "roomNumber", source = "room.roomNumber")
    @Mapping(target = "hospitalizationId", source = "hospitalization.id")
    @Mapping(target = "currentPatientName", expression = "java(entity.getHospitalization() != null && entity.getHospitalization().getPatient() != null ? entity.getHospitalization().getPatient().getFullName() : null)")
    @Mapping(target = "externalId", source = "externalId")
    BedDto toDto(Bed entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "hospitalization.id", source = "hospitalizationId")
    @Mapping(target = "isDeleted", ignore = true)
    void updateEntityFromDto(BedDto dto, @MappingTarget Bed entity);
}
