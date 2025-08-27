package com.amachi.app.vitalia.nurse.mapper;

import com.amachi.app.vitalia.address.mapper.AddressMapper;
import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.hospital.mapper.HospitalMapperSupport;
import com.amachi.app.vitalia.nurse.dto.NurseDto;
import com.amachi.app.vitalia.nurse.entity.Nurse;
import com.amachi.app.vitalia.nurseprofessionspeciality.mapper.NurseProfessionSpecialityMapper;
import com.amachi.app.vitalia.user.mapper.UserMapper;
import com.amachi.app.vitalia.user.mapper.UserProfileMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                NurseProfessionSpecialityMapper.class,
                UserProfileMapper.class,
                AddressMapper.class,
                UserMapper.class,
                HospitalMapperSupport.class,
        }
)

public interface NurseMapper extends EntityDtoMapper<Nurse, NurseDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "hospitalPrincipal.id", source = "hospitalPrincipalId")
    @Mapping(target = "hospitales", source = "hospitalesIds" , qualifiedByName = "hospitalesIdsToHospitalSet")
    Nurse toEntity(NurseDto dto);

    @Override
    @Mapping(target = "hospitalPrincipalId", source = "hospitalPrincipal.id")
    @Mapping(target = "hospitalesIds", source = "hospitales", qualifiedByName = "hospitalToHospitalesIdsSet")
    NurseDto toDto(Nurse entity);
}
