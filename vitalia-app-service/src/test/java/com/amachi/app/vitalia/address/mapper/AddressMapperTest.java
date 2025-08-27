package com.amachi.app.vitalia.address.mapper;

import com.amachi.app.vitalia.address.dto.AddressDto;
import com.amachi.app.vitalia.address.entity.Address;
import com.amachi.app.vitalia.country.mapper.CountryMapper;
import com.amachi.app.vitalia.departamento.mapper.DepartamentoMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = configureAddressMapper();
    }

    @Test
    void shouldMapAddressDtoToEntity() {
        AddressDto dto = Instancio.create(AddressDto.class);
        Address entity = addressMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getDireccion(), entity.getDireccion());
        assertEquals(dto.getCiudad(), entity.getCiudad());
        assertEquals(dto.getCasillaPostal(), entity.getCasillaPostal());
        assertEquals(dto.getNumero(), entity.getNumero());
        assertNotNull(entity.getCountry());
        assertEquals(dto.getCountry().getName(), entity.getCountry().getName());
        assertEquals(dto.getCountry().getIso(), entity.getCountry().getIso());
        assertEquals(dto.getCountry().getId(), entity.getCountry().getId());

        assertNotNull(entity.getDepartamento());
        assertEquals(dto.getDepartamento().getId(), entity.getDepartamento().getId());
        assertEquals(dto.getDepartamento().getNombre(), entity.getDepartamento().getNombre());
        assertEquals(dto.getDepartamento().getPoblacion(), entity.getDepartamento().getPoblacion());
        assertEquals(dto.getDepartamento().getCountryId(), entity.getDepartamento().getCountry().getId());
    }

    @Test
    void shouldMapAddressToDto() {
        Address entity = Instancio.create(Address.class);
        AddressDto dto = addressMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getDireccion(), dto.getDireccion());
        assertEquals(entity.getCiudad(), dto.getCiudad());
        assertEquals(entity.getCasillaPostal(), dto.getCasillaPostal());
        assertEquals(entity.getNumero(), dto.getNumero());
        assertNotNull(entity.getCountry());
        assertEquals(entity.getCountry().getName(), dto.getCountry().getName());
        assertEquals(entity.getCountry().getIso(), dto.getCountry().getIso());
        assertEquals(entity.getCountry().getId(), dto.getCountry().getId());
        assertNotNull(entity.getDepartamento());
        assertEquals(entity.getDepartamento().getId(), dto.getDepartamento().getId());
        assertEquals(entity.getDepartamento().getNombre(), dto.getDepartamento().getNombre());
        assertEquals(entity.getDepartamento().getPoblacion(), dto.getDepartamento().getPoblacion());
        assertEquals(entity.getDepartamento().getCountry().getId(), dto.getDepartamento().getCountryId());
    }

    private AddressMapper configureAddressMapper() {
        return new AddressMapperImpl(getMapper(CountryMapper.class), getMapper(DepartamentoMapper.class));
    }

    private <T> T getMapper(Class<T> mapperClass) {
        return Mappers.getMapper(mapperClass);
    }
}