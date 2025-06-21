package com.amachi.app.vitalia.country.mapper;

import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.GenericMapperConfig;
import com.amachi.app.vitalia.country.dto.CountryDto;
import com.amachi.app.vitalia.country.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = GenericMapperConfig.class)
public interface CountryMapper extends EntityDtoMapper<Country, CountryDto> {



//    @Mapping(target = "createdBy", ignore = true)
//    @Mapping(target = "createdDate", ignore = true)
//    @Mapping(target = "lastModifiedBy", ignore = true)
//    @Mapping(target = "lastModifiedDate", ignore = true)
//    Country toEntity(CountryDto dto);
//
////    @Mapping(target = "createdBy", ignore = true)
////    @Mapping(target = "createdDate", ignore = true)
////    @Mapping(target = "lastModifiedBy", ignore = true)
////    @Mapping(target = "lastModifiedDate", ignore = true)
//    CountryDto toDto(Country entity);
}