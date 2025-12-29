package com.amachi.app.vitalia.geography.country.mapper;

import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.geography.country.dto.CountryDto;
import com.amachi.app.vitalia.geography.country.entity.Country;
import org.mapstruct.Mapper;

import org.mapstruct.Builder;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface CountryMapper extends EntityDtoMapper<Country, CountryDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    Country toEntity(CountryDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CountryDto dto, @MappingTarget Country entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    CountryDto toDto(Country entity);
}