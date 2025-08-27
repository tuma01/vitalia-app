package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.historiamedica.dto.RegistroEnfermedadDto;
import com.amachi.app.vitalia.historiamedica.entity.RegistroEnfermedad;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface RegistroEnfermedadMapper extends EntityDtoMapper<RegistroEnfermedad, RegistroEnfermedadDto> {

    @Override
    @Mapping(target = "historiaMedica.id", source = "historiaMedicaId")
    RegistroEnfermedad toEntity(RegistroEnfermedadDto dto);

    @Override
    @Mapping(target = "historiaMedicaId", source = "historiaMedica.id")
    RegistroEnfermedadDto toDto(RegistroEnfermedad entity);
}
