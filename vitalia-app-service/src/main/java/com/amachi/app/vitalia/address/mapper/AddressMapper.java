package com.amachi.app.vitalia.address.mapper;

import com.amachi.app.vitalia.address.dto.AddressDto;
import com.amachi.app.vitalia.address.entity.Address;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.country.mapper.CountryMapper;
import com.amachi.app.vitalia.departamento.mapper.DepartamentoMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                CountryMapper.class,
                DepartamentoMapper.class
        }
)
public interface AddressMapper extends EntityDtoMapper<Address, AddressDto> {

    @Override
    Address toEntity(AddressDto dto);

    @Override
    AddressDto toDto(Address entity);
}