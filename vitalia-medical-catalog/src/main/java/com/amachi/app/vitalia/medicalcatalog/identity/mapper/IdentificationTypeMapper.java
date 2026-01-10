package com.amachi.app.vitalia.medicalcatalog.identity.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.IdentificationTypeDto;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface IdentificationTypeMapper extends EntityDtoMapper<IdentificationType, IdentificationTypeDto> {
    @Override @AuditableIgnoreConfig.IgnoreAuditableFields
    IdentificationType toEntity(IdentificationTypeDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(IdentificationTypeDto dto, @MappingTarget IdentificationType entity);

    @Override @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    IdentificationTypeDto toDto(IdentificationType entity);
}
