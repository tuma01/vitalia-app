package com.amachi.app.vitalia.hospital.mapper;

import com.amachi.app.vitalia.address.mapper.AddressMapper;
import com.amachi.app.vitalia.common.mapper.*;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoHospitalMapper;
import com.amachi.app.vitalia.hospital.dto.HospitalDto;
import com.amachi.app.vitalia.hospital.entity.Hospital;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                AddressMapper.class,
                DepartamentoHospitalMapper.class
        }
)
public interface HospitalMapper extends EntityDtoMapper<Hospital, HospitalDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    Hospital toEntity(HospitalDto dto);

    @Override
    HospitalDto toDto(Hospital entity);
}
