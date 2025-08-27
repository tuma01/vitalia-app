package com.amachi.app.vitalia.municipio.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.municipio.dto.MunicipioDto;
import com.amachi.app.vitalia.municipio.entity.Municipio;
import com.amachi.app.vitalia.provincia.mapper.ProvinciaMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                ProvinciaMapper.class
        }
)
public interface MunicipioMapper extends EntityDtoMapper<Municipio, MunicipioDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    Municipio toEntity(MunicipioDto dto);

    @Override
    MunicipioDto toDto(Municipio entity);
}
