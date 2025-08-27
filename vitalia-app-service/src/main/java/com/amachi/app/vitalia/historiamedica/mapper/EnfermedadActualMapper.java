package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.historiamedica.dto.EnfermedadActualDto;
import com.amachi.app.vitalia.historiamedica.entity.EnfermedadActual;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface EnfermedadActualMapper extends EntityDtoMapper<EnfermedadActual, EnfermedadActualDto> {
    @Override
    @Mapping(target = "historiaMedica.id", source = "historiaMedicaId")
    EnfermedadActual toEntity(EnfermedadActualDto dto);

    @Override
    @Mapping(target = "historiaMedicaId", source = "historiaMedica.id")
    EnfermedadActualDto toDto(EnfermedadActual entity);
}
