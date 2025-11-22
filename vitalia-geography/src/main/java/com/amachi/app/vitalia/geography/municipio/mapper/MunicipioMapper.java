package com.amachi.app.vitalia.geography.municipio.mapper;

import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.geography.municipio.dto.MunicipioDto;
import com.amachi.app.vitalia.geography.municipio.entity.Municipio;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface MunicipioMapper extends EntityDtoMapper<Municipio, MunicipioDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "provincia.id", source = "provinciaId")
    Municipio toEntity(MunicipioDto dto);

    @Override
    @Mapping(target = "provinciaId", source = "provincia.id")
    MunicipioDto toDto(Municipio entity);
}
