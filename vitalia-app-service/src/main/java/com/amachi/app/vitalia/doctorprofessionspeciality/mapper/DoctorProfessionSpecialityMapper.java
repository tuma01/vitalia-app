package com.amachi.app.vitalia.doctorprofessionspeciality.mapper;

import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.GenericMapperConfig;
import com.amachi.app.vitalia.doctorprofessionspeciality.dto.DoctorProfessionSpecialityDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.entity.DoctorProfessionSpeciality;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = GenericMapperConfig.class)
public interface DoctorProfessionSpecialityMapper extends EntityDtoMapper<DoctorProfessionSpeciality, DoctorProfessionSpecialityDto> {

    DoctorProfessionSpecialityMapper INSTANCE = Mappers.getMapper(DoctorProfessionSpecialityMapper.class);

    @Override
    DoctorProfessionSpeciality toEntity(DoctorProfessionSpecialityDto dto);

    @Override
    DoctorProfessionSpecialityDto toDto(DoctorProfessionSpeciality entity);
}