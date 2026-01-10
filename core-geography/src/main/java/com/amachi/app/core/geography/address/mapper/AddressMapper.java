package com.amachi.app.core.geography.address.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface AddressMapper extends EntityDtoMapper<Address, AddressDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "country.id", source = "countryId")
    @Mapping(target = "departamento.id", source = "departamentoId")
    Address toEntity(AddressDto dto);

    @Override
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "departamentoId", source = "departamento.id")
    AddressDto toDto(Address entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "country.id", source = "countryId")
    @Mapping(target = "departamento.id", source = "departamentoId")
    void updateEntityFromDto(AddressDto dto, @MappingTarget Address entity);
}