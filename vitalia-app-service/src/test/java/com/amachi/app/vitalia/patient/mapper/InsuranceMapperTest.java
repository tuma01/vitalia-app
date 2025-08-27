package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.address.mapper.AddressMapperImpl;
import com.amachi.app.vitalia.country.mapper.CountryMapper;
import com.amachi.app.vitalia.departamento.mapper.DepartamentoMapper;
import com.amachi.app.vitalia.patient.dto.InsuranceDto;
import com.amachi.app.vitalia.patient.entity.Insurance;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceMapperTest {

    private InsuranceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new InsuranceMapperImpl(
                new AddressMapperImpl(
                        Mappers.getMapper(CountryMapper.class),
                        Mappers.getMapper(DepartamentoMapper.class)
                )
        );
    }


    @Test
    void shouldMapInsuranceDtoToEntity() {
        // Given
        var dto = Instancio.create(InsuranceDto.class);

        // When
        var entity = mapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getInsuranceNumber(), entity.getInsuranceNumber());
        assertNotNull(entity.getAddress());
        assertEquals(dto.getAddress().getDireccion(), entity.getAddress().getDireccion());
        assertEquals(dto.getAddress().getCiudad(), entity.getAddress().getCiudad());
        assertEquals(dto.getAddress().getCasillaPostal(), entity.getAddress().getCasillaPostal());
        assertEquals(dto.getAddress().getNumero(), entity.getAddress().getNumero());
        assertNotNull(entity.getAddress().getCountry());
        assertEquals(dto.getAddress().getCountry().getName(), entity.getAddress().getCountry().getName());
        assertEquals(dto.getAddress().getCountry().getIso(), entity.getAddress().getCountry().getIso());
        assertEquals(dto.getAddress().getCountry().getId(), entity.getAddress().getCountry().getId());
        assertNotNull(entity.getAddress().getDepartamento());
        assertEquals(dto.getAddress().getDepartamento().getId(), entity.getAddress().getDepartamento().getId());
        assertEquals(dto.getAddress().getDepartamento().getNombre(), entity.getAddress().getDepartamento().getNombre());
        assertEquals(dto.getAddress().getDepartamento().getPoblacion(), entity.getAddress().getDepartamento().getPoblacion());
        assertEquals(dto.getAddress().getDepartamento().getCountryId(), entity.getAddress().getDepartamento().getCountry().getId());
    }

    @Test
    void shouldMapInsuranceEntityToDto() {
        // Given
        var entity = Instancio.create(Insurance.class);
        // When
        var dto = mapper.toDto(entity);
        // Then
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getInsuranceNumber(), dto.getInsuranceNumber());
        assertNotNull(dto.getAddress());
        assertEquals(entity.getAddress().getDireccion(), dto.getAddress().getDireccion());
        assertEquals(entity.getAddress().getCiudad(), dto.getAddress().getCiudad());
        assertEquals(entity.getAddress().getCasillaPostal(), dto.getAddress().getCasillaPostal());
        assertEquals(entity.getAddress().getNumero(), dto.getAddress().getNumero());
        assertNotNull(dto.getAddress().getCountry());
        assertEquals(entity.getAddress().getCountry().getName(), dto.getAddress().getCountry().getName());
        assertEquals(entity.getAddress().getCountry().getIso(), dto.getAddress().getCountry().getIso());
        assertEquals(entity.getAddress().getCountry().getId(), dto.getAddress().getCountry().getId());
        assertNotNull(dto.getAddress().getDepartamento());
        assertEquals(entity.getAddress().getDepartamento().getId(), dto.getAddress().getDepartamento().getId());
        assertEquals(entity.getAddress().getDepartamento().getNombre(), dto.getAddress().getDepartamento().getNombre());
        assertEquals(entity.getAddress().getDepartamento().getPoblacion(), dto.getAddress().getDepartamento().getPoblacion());
        assertEquals(entity.getAddress().getDepartamento().getCountry().getId(), dto.getAddress().getDepartamento().getCountryId());
    }
}