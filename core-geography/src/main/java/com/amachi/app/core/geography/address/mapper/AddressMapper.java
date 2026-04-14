package com.amachi.app.core.geography.address.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.country.repository.CountryRepository;
import com.amachi.app.core.geography.state.entity.State;
import com.amachi.app.core.geography.state.repository.StateRepository;
import com.amachi.app.core.geography.municipality.entity.Municipality;
import com.amachi.app.core.geography.municipality.repository.MunicipalityRepository;
import com.amachi.app.core.geography.province.entity.Province;
import com.amachi.app.core.geography.province.repository.ProvinceRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public abstract class AddressMapper implements EntityDtoMapper<Address, AddressDto> {

    @Autowired
    protected CountryRepository countryRepository;

    @Autowired
    protected StateRepository stateRepository;

    @Autowired
    protected ProvinceRepository provinceRepository;

    @Autowired
    protected MunicipalityRepository municipalityRepository;

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "country", source = "countryId", qualifiedByName = "loadCountry")
    @Mapping(target = "state", source = "stateId", qualifiedByName = "loadState")
    @Mapping(target = "province", source = "provinceId", qualifiedByName = "loadProvince")
    @Mapping(target = "municipality", source = "municipalityId", qualifiedByName = "loadMunicipality")
    public abstract Address toEntity(AddressDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "stateId", source = "state.id")
    @Mapping(target = "provinceId", source = "province.id")
    @Mapping(target = "municipalityId", source = "municipality.id")
    public abstract AddressDto toDto(Address entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "country", source = "countryId", qualifiedByName = "loadCountry")
    @Mapping(target = "state", source = "stateId", qualifiedByName = "loadState")
    @Mapping(target = "province", source = "provinceId", qualifiedByName = "loadProvince")
    @Mapping(target = "municipality", source = "municipalityId", qualifiedByName = "loadMunicipality")
    public abstract void updateEntityFromDto(AddressDto dto, @MappingTarget Address entity);

    @Named("loadCountry")
    protected Country loadCountry(Long id) {
        if (id == null) return null;
        return countryRepository.findById(id).orElse(null);
    }

    @Named("loadState")
    protected State loadState(Long id) {
        if (id == null) return null;
        return stateRepository.findById(id).orElse(null);
    }

    @Named("loadProvince")
    protected Province loadProvince(Long id) {
        if (id == null) return null;
        return provinceRepository.findById(id).orElse(null);
    }

    @Named("loadMunicipality")
    protected Municipality loadMunicipality(Long id) {
        if (id == null) return null;
        return municipalityRepository.findById(id).orElse(null);
    }
}
