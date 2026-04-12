package com.amachi.app.core.geography.address.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.country.repository.CountryRepository;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import com.amachi.app.core.geography.departamento.repository.DepartamentoRepository;
import com.amachi.app.core.geography.municipio.entity.Municipio;
import com.amachi.app.core.geography.municipio.repository.MunicipioRepository;
import com.amachi.app.core.geography.provincia.entity.Provincia;
import com.amachi.app.core.geography.provincia.repository.ProvinciaRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public abstract class AddressMapper implements EntityDtoMapper<Address, AddressDto> {

    @Autowired
    protected CountryRepository countryRepository;

    @Autowired
    protected DepartamentoRepository departamentoRepository;

    @Autowired
    protected ProvinciaRepository provinciaRepository;

    @Autowired
    protected MunicipioRepository municipioRepository;

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
    protected Departamento loadDepartamento(Long id) {
        if (id == null) return null;
        return departamentoRepository.findById(id).orElse(null);
    }

    @Named("loadProvincia")
    protected Provincia loadProvincia(Long id) {
        if (id == null) return null;
        return provinciaRepository.findById(id).orElse(null);
    }

    @Named("loadMunicipio")
    protected Municipio loadMunicipio(Long id) {
        if (id == null) return null;
        return municipioRepository.findById(id).orElse(null);
    }
}
