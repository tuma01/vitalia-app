package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.patient.dto.HabitoFisiologicoDto;
import com.amachi.app.vitalia.patient.entity.HabitoFisiologico;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface HabitoFisiologicoMapper extends EntityDtoMapper<HabitoFisiologico, HabitoFisiologicoDto> {

    @Override
    HabitoFisiologico toEntity(HabitoFisiologicoDto dto);

    @Override
    HabitoFisiologicoDto toDto(HabitoFisiologico entity);
}
