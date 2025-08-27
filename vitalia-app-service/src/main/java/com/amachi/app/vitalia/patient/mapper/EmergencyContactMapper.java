package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.patient.dto.EmergencyContactDto;
import com.amachi.app.vitalia.patient.entity.EmergencyContact;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface EmergencyContactMapper extends EntityDtoMapper<EmergencyContact, EmergencyContactDto> {

    @Override
    EmergencyContact toEntity(EmergencyContactDto dto);

    @Override
    EmergencyContactDto toDto(EmergencyContact entity);
}
