package com.amachi.app.core.geography.address;

import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import com.amachi.app.core.geography.provincia.entity.Provincia;
import com.amachi.app.core.geography.municipio.entity.Municipio;
import com.amachi.app.core.geography.country.repository.CountryRepository;
import com.amachi.app.core.geography.departamento.repository.DepartamentoRepository;
import com.amachi.app.core.geography.provincia.repository.ProvinciaRepository;
import com.amachi.app.core.geography.municipio.repository.MunicipioRepository;
import com.amachi.app.core.geography.address.mapper.AddressMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressMapperTest {

    @Mock
    private CountryRepository countryRepository;
    @Mock
    private DepartamentoRepository departamentoRepository;
    @Mock
    private ProvinciaRepository provinciaRepository;
    @Mock
    private MunicipioRepository municipioRepository;

    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = Mappers.getMapper(AddressMapper.class);
        ReflectionTestUtils.setField(addressMapper, "countryRepository", countryRepository);
        ReflectionTestUtils.setField(addressMapper, "departamentoRepository", departamentoRepository);
        ReflectionTestUtils.setField(addressMapper, "provinciaRepository", provinciaRepository);
        ReflectionTestUtils.setField(addressMapper, "municipioRepository", municipioRepository);
    }

    @Test
    void shouldMapAddressDtoToEntity() {
        AddressDto dto = Instancio.create(AddressDto.class);

        // Mocking repo lookups
        when(countryRepository.findById(dto.getCountryId())).thenReturn(Optional.of(Country.builder().id(dto.getCountryId()).build()));
        when(departamentoRepository.findById(dto.getDepartamentoId())).thenReturn(Optional.of(Departamento.builder().id(dto.getDepartamentoId()).build()));
        when(provinciaRepository.findById(dto.getProvinciaId())).thenReturn(Optional.of(Provincia.builder().id(dto.getProvinciaId()).build()));
        when(municipioRepository.findById(dto.getMunicipioId())).thenReturn(Optional.of(Municipio.builder().id(dto.getMunicipioId()).build()));

        Address entity = addressMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getDireccion(), entity.getDireccion());
        assertEquals(dto.getCiudad(), entity.getCiudad());
        assertNotNull(entity.getCountry());
        assertEquals(dto.getCountryId(), entity.getCountry().getId());
        assertNotNull(entity.getMunicipio());
        assertEquals(dto.getMunicipioId(), entity.getMunicipio().getId());
    }

    @Test
    void shouldMapAddressToDto() {
        Address entity = Instancio.create(Address.class);
        AddressDto dto = addressMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getDireccion(), dto.getDireccion());
        assertEquals(entity.getCountry().getId(), dto.getCountryId());
        assertEquals(entity.getMunicipio().getId(), dto.getMunicipioId());
    }
}
