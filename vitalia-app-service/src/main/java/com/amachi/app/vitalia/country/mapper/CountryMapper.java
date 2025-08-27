package com.amachi.app.vitalia.country.mapper;

import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.country.dto.CountryDto;
import com.amachi.app.vitalia.country.entity.Country;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface CountryMapper extends EntityDtoMapper<Country, CountryDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    Country toEntity(CountryDto dto);

    @Override
    CountryDto toDto(Country entity);
}