package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.historiamedica.dto.HistoriaMedicaDto;
import com.amachi.app.vitalia.historiamedica.entity.HistoriaMedica;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                ConsultaMedicaMapper.class,
                EnfermedadActualMapper.class,
                RegistroEnfermedadMapper.class
        }
)
public interface HistoriaMedicaMapper extends EntityDtoMapper<HistoriaMedica, HistoriaMedicaDto> {

    @Override
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "hospital.id", source = "hospitalId")
    HistoriaMedica toEntity(HistoriaMedicaDto dto);

    @Override
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "hospitalId", source = "hospital.id")
    HistoriaMedicaDto toDto(HistoriaMedica entity);
}
