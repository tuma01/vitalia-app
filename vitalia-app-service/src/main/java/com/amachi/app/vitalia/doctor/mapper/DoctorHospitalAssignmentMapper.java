package com.amachi.app.vitalia.doctor.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.doctor.dto.DoctorHospitalAssignmentDto;
import com.amachi.app.vitalia.doctor.entity.DoctorHospitalAssignment;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface DoctorHospitalAssignmentMapper extends EntityDtoMapper<DoctorHospitalAssignment, DoctorHospitalAssignmentDto> {

    @Override
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "hospital.id", source = "hospitalId")
    DoctorHospitalAssignment toEntity(DoctorHospitalAssignmentDto dto);

    @Override
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "hospitalId", source = "hospital.id")
    DoctorHospitalAssignmentDto toDto(DoctorHospitalAssignment entity);
}
