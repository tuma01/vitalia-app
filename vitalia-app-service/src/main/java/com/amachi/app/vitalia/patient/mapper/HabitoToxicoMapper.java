package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.patient.dto.HabitoToxicoDto;
import com.amachi.app.vitalia.patient.entity.HabitoToxico;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface HabitoToxicoMapper extends EntityDtoMapper<HabitoToxico, HabitoToxicoDto> {

    @Override
    HabitoToxico toEntity(HabitoToxicoDto dto);

    @Override
    HabitoToxicoDto toDto(HabitoToxico entity);
}
