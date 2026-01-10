package com.amachi.app.core.geography.address.mapper;

import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.country.mapper.CountryMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private AddressMapper addressMapper;
    @BeforeEach
    void setUp() {
        addressMapper = Mappers.getMapper(AddressMapper.class);
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
        assertEquals(dto.getCountryId(), entity.getCountry().getId());
        assertNotNull(entity.getDepartamento());
        assertEquals(dto.getDepartamentoId(), entity.getDepartamento().getId());
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

        assertEquals(entity.getCountry().getId(), dto.getCountryId());
        assertNotNull(entity.getDepartamento());
        assertEquals(entity.getDepartamento().getId(), dto.getDepartamentoId());
    }
}