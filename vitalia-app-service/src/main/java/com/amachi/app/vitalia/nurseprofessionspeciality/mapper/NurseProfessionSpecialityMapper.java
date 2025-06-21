package com.amachi.app.vitalia.nurseprofessionspeciality.mapper;

import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.GenericMapperConfig;
import com.amachi.app.vitalia.nurseprofessionspeciality.dto.NurseProfessionSpecialityDto;
import com.amachi.app.vitalia.nurseprofessionspeciality.entity.NurseProfessionSpeciality;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = GenericMapperConfig.class)
public interface NurseProfessionSpecialityMapper extends EntityDtoMapper<NurseProfessionSpeciality, NurseProfessionSpecialityDto> {

    NurseProfessionSpecialityMapper INSTANCE = Mappers.getMapper(NurseProfessionSpecialityMapper.class);

    @Override
    NurseProfessionSpeciality toEntity(NurseProfessionSpecialityDto dto);

    @Override
    NurseProfessionSpecialityDto toDto(NurseProfessionSpeciality entity);
}
