package com.amachi.app.vitalia.nurse.mapper;

import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.GenericMapperConfig;
import com.amachi.app.vitalia.nurse.dto.NurseDto;
import com.amachi.app.vitalia.nurse.entity.Nurse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = GenericMapperConfig.class)
public interface NurseMapper extends EntityDtoMapper<Nurse, NurseDto> {

    NurseMapper INSTANCE = Mappers.getMapper(NurseMapper.class);

    @Override
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Nurse toEntity(NurseDto dto);

    @Override
    NurseDto toDto(Nurse entity);
}
