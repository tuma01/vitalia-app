package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.patient.dto.HistoriaFamiliarDto;
import com.amachi.app.vitalia.patient.entity.HistoriaFamiliar;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface HistoriaFamiliarMapper extends EntityDtoMapper<HistoriaFamiliar, HistoriaFamiliarDto> {

    @Override
    HistoriaFamiliar toEntity(HistoriaFamiliarDto dto);

    @Override
    HistoriaFamiliarDto toDto(HistoriaFamiliar entity);
}
