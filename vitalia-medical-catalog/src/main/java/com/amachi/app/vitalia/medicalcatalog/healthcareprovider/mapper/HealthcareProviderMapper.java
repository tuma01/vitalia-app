package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.HealthcareProviderDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import com.amachi.app.core.geography.address.mapper.AddressMapper;
import org.mapstruct.*;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(config = BaseMapperConfig.class, uses = { AddressMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface HealthcareProviderMapper extends EntityDtoMapper<HealthcareProvider, HealthcareProviderDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    HealthcareProvider toEntity(HealthcareProviderDto dto);
 
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(HealthcareProviderDto dto, @MappingTarget HealthcareProvider entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    HealthcareProviderDto toDto(HealthcareProvider entity);
}
