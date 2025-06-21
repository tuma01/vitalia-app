package com.amachi.app.vitalia.municipio.mapper;

import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.GenericMapperConfig;
import com.amachi.app.vitalia.municipio.dto.MunicipioDto;
import com.amachi.app.vitalia.municipio.entity.Municipio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = GenericMapperConfig.class)
public interface MunicipioMapper extends EntityDtoMapper<Municipio, MunicipioDto> {

    MunicipioMapper INSTANCE = Mappers.getMapper(MunicipioMapper.class);

    @Override
    MunicipioDto toDto(Municipio entity);

    @Override
    Municipio toEntity(MunicipioDto dto);
}
