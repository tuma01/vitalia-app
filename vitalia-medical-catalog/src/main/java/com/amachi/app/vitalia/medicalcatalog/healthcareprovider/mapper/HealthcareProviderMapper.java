package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.HealthcareProviderDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface HealthcareProviderMapper extends EntityDtoMapper<HealthcareProvider, HealthcareProviderDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    HealthcareProvider toEntity(HealthcareProviderDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(HealthcareProviderDto dto, @MappingTarget HealthcareProvider entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    HealthcareProviderDto toDto(HealthcareProvider entity);
}
