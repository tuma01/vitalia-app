package com.amachi.app.vitalia.doctor.mapper;

import com.amachi.app.vitalia.address.mapper.AddressMapper;
import com.amachi.app.vitalia.common.mapper.*;
import com.amachi.app.vitalia.doctor.dto.DoctorDto;
import com.amachi.app.vitalia.doctor.entity.Doctor;
import com.amachi.app.vitalia.doctorprofessionspeciality.mapper.DoctorProfessionSpecialityMapper;
import com.amachi.app.vitalia.historiamedica.mapper.HistoriaMedicaMapper;
import com.amachi.app.vitalia.hospital.mapper.HospitalMapperSupport;
import com.amachi.app.vitalia.user.mapper.UserMapper;
import com.amachi.app.vitalia.user.mapper.UserProfileMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        implementationName = "DoctorMapperImpl",
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                DoctorProfessionSpecialityMapper.class,
                DoctorHospitalAssignmentMapper.class,
                HistoriaMedicaMapper.class,
                UserProfileMapper.class,
                AddressMapper.class,
                UserMapper.class,
                HospitalMapperSupport.class
        }
)
public interface DoctorMapper extends EntityDtoMapper<Doctor, DoctorDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "hospitalPrincipal.id", source = "hospitalPrincipalId")
    @Mapping(target = "hospitales", source = "hospitalesIds" , qualifiedByName = "hospitalesIdsToHospitalSet")
    Doctor toEntity(DoctorDto dto);

    @Override
    @Mapping(target = "hospitalPrincipalId", source = "hospitalPrincipal.id")
    @Mapping(target = "hospitalesIds", source = "hospitales", qualifiedByName = "hospitalToHospitalesIdsSet")
    DoctorDto toDto(Doctor entity);
}
