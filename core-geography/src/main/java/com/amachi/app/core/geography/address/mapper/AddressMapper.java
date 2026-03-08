package com.amachi.app.core.geography.address.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public abstract class AddressMapper implements EntityDtoMapper<Address, AddressDto> {

    @Autowired
    protected com.amachi.app.core.geography.country.repository.CountryRepository countryRepository;
    @Autowired
    protected com.amachi.app.core.geography.departamento.repository.DepartamentoRepository departamentoRepository;
    @Autowired
    protected com.amachi.app.core.geography.provincia.repository.ProvinciaRepository provinciaRepository;
    @Autowired
    protected com.amachi.app.core.geography.municipio.repository.MunicipioRepository municipioRepository;

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "country", source = "countryId", qualifiedByName = "loadCountry")
    @Mapping(target = "departamento", source = "departamentoId", qualifiedByName = "loadDepartamento")
    @Mapping(target = "provincia", source = "provinciaId", qualifiedByName = "loadProvincia")
    @Mapping(target = "municipio", source = "municipioId", qualifiedByName = "loadMunicipio")
    public abstract Address toEntity(AddressDto dto);

    @Override
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "departamentoId", source = "departamento.id")
    @Mapping(target = "provinciaId", source = "provincia.id")
    @Mapping(target = "municipioId", source = "municipio.id")
    public abstract AddressDto toDto(Address entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "country", source = "countryId", qualifiedByName = "loadCountry")
    @Mapping(target = "departamento", source = "departamentoId", qualifiedByName = "loadDepartamento")
    @Mapping(target = "provincia", source = "provinciaId", qualifiedByName = "loadProvincia")
    @Mapping(target = "municipio", source = "municipioId", qualifiedByName = "loadMunicipio")
    public abstract void updateEntityFromDto(AddressDto dto, @MappingTarget Address entity);

    @Named("loadCountry")
    protected com.amachi.app.core.geography.country.entity.Country loadCountry(Long id) {
        if (id == null) return null;
        return countryRepository.findById(id).orElse(null);
    }

    @Named("loadDepartamento")
    protected com.amachi.app.core.geography.departamento.entity.Departamento loadDepartamento(Long id) {
        if (id == null) return null;
        return departamentoRepository.findById(id).orElse(null);
    }

    @Named("loadProvincia")
    protected com.amachi.app.core.geography.provincia.entity.Provincia loadProvincia(Long id) {
        if (id == null) return null;
        return provinciaRepository.findById(id).orElse(null);
    }

    @Named("loadMunicipio")
    protected com.amachi.app.core.geography.municipio.entity.Municipio loadMunicipio(Long id) {
        if (id == null) return null;
        return municipioRepository.findById(id).orElse(null);
    }
}