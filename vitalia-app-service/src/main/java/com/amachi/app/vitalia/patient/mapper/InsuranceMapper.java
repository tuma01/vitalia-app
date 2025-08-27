package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.address.mapper.AddressMapper;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.patient.dto.InsuranceDto;
import com.amachi.app.vitalia.patient.entity.Insurance;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                AddressMapper.class
        }
)
public interface InsuranceMapper extends EntityDtoMapper<Insurance, InsuranceDto> {

    @Override
    Insurance toEntity(InsuranceDto dto);

    @Override
    InsuranceDto toDto(Insurance entity);
}
