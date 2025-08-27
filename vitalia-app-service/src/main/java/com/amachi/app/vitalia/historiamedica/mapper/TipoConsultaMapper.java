package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.historiamedica.dto.TipoConsultaDto;
import com.amachi.app.vitalia.historiamedica.entity.TipoConsulta;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface TipoConsultaMapper extends EntityDtoMapper<TipoConsulta, TipoConsultaDto> {

    @Override
    TipoConsulta toEntity(TipoConsultaDto dto);

    @Override
    TipoConsultaDto toDto(TipoConsulta entity);
}
