package com.amachi.app.vitalia.provincia.mapper;


import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.GenericMapperConfig;
import com.amachi.app.vitalia.provincia.dto.ProvinciaDto;
import com.amachi.app.vitalia.provincia.entity.Provincia;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = GenericMapperConfig.class)
public interface ProvinciaMapper extends EntityDtoMapper<Provincia, ProvinciaDto> {

    ProvinciaMapper INSTANCE = Mappers.getMapper(ProvinciaMapper.class);

    @Override
//	@Mapping(target = "country", source = "country")
    ProvinciaDto toDto(Provincia entity);

    @Override
//	@Mapping(target = "country", source = "country")
    Provincia toEntity(ProvinciaDto dto);
}
