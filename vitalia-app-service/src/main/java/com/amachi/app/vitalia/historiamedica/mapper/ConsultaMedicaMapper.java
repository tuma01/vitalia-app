package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.historiamedica.dto.ConsultaMedicaDto;
import com.amachi.app.vitalia.historiamedica.entity.ConsultaMedica;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                TipoConsultaMapper.class
        }
)
public interface ConsultaMedicaMapper extends EntityDtoMapper<ConsultaMedica, ConsultaMedicaDto> {

    @Override
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "historiaMedica.id", source = "historiaMedicaId")
    ConsultaMedica toEntity(ConsultaMedicaDto dto);

    @Override
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "historiaMedicaId", source = "historiaMedica.id")
    ConsultaMedicaDto toDto(ConsultaMedica entity);
}
