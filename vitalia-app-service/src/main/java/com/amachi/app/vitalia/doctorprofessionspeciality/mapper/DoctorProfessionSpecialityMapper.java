package com.amachi.app.vitalia.doctorprofessionspeciality.mapper;

import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.doctorprofessionspeciality.dto.DoctorProfessionSpecialityDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.entity.DoctorProfessionSpeciality;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface DoctorProfessionSpecialityMapper extends EntityDtoMapper<DoctorProfessionSpeciality, DoctorProfessionSpecialityDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    DoctorProfessionSpeciality toEntity(DoctorProfessionSpecialityDto dto);

    @Override
    DoctorProfessionSpecialityDto toDto(DoctorProfessionSpeciality entity);
}