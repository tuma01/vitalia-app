package com.amachi.app.vitalia.hospital.mapper;

import com.amachi.app.vitalia.address.mapper.AddressMapperImpl;
import com.amachi.app.vitalia.country.mapper.CountryMapper;
import com.amachi.app.vitalia.departamento.mapper.DepartamentoMapper;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoHospitalMapperImpl;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoTipoMapper;
import com.amachi.app.vitalia.hospital.dto.HospitalDto;
import com.amachi.app.vitalia.hospital.entity.Hospital;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HospitalMapperTest {
    private HospitalMapper hospitalMapper;

    @BeforeEach
    void setUp() {
        hospitalMapper = new HospitalMapperImpl(
                new AddressMapperImpl(getMapper(CountryMapper.class),
                        getMapper(DepartamentoMapper.class)),
                new DepartamentoHospitalMapperImpl(getMapper(DepartamentoTipoMapper.class)));
    }

    @Test
    void shouldMapHospitalDtoToEntity() {
         // given
        HospitalDto dto = Instancio.create(HospitalDto.class);

        // when
        Hospital entity = hospitalMapper.toEntity(dto);

        // then
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDescription(), entity.getDescription());
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
        assertNotNull(entity.getDepartamentoHospitals());
        assertEquals(dto.getDepartamentoHospitals().size(), entity.getDepartamentoHospitals().size());
    }

    @Test
    void shouldMapHospitalToDto() {
        // given
        Hospital entity = Instancio.create(Hospital.class);

        // when
        HospitalDto dto = hospitalMapper.toDto(entity);

        // then
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
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
        assertNotNull(dto.getDepartamentoHospitals());
        assertEquals(entity.getDepartamentoHospitals().size(), dto.getDepartamentoHospitals().size());
    }

    private <T> T getMapper(Class<T> mapperClass) {
        return Mappers.getMapper(mapperClass);
    }
}