package com.amachi.app.core.geography.address;

import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.state.entity.State;
import com.amachi.app.core.geography.province.entity.Province;
import com.amachi.app.core.geography.municipality.entity.Municipality;
import com.amachi.app.core.geography.country.repository.CountryRepository;
import com.amachi.app.core.geography.state.repository.StateRepository;
import com.amachi.app.core.geography.province.repository.ProvinceRepository;
import com.amachi.app.core.geography.municipality.repository.MunicipalityRepository;
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
    private StateRepository stateRepository;
    @Mock
    private ProvinceRepository provinceRepository;
    @Mock
    private MunicipalityRepository municipalityRepository;

    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = Mappers.getMapper(AddressMapper.class);
        ReflectionTestUtils.setField(addressMapper, "countryRepository", countryRepository);
        ReflectionTestUtils.setField(addressMapper, "stateRepository", stateRepository);
        ReflectionTestUtils.setField(addressMapper, "provinceRepository", provinceRepository);
        ReflectionTestUtils.setField(addressMapper, "municipalityRepository", municipalityRepository);
    }

    @Test
    void shouldMapAddressDtoToEntity() {
        AddressDto dto = Instancio.create(AddressDto.class);

        // Mocking repo lookups
        when(countryRepository.findById(dto.getCountryId())).thenReturn(Optional.of(Country.builder().id(dto.getCountryId()).build()));
        when(stateRepository.findById(dto.getStateId())).thenReturn(Optional.of(State.builder().id(dto.getStateId()).build()));
        when(provinceRepository.findById(dto.getProvinceId())).thenReturn(Optional.of(Province.builder().id(dto.getProvinceId()).build()));
        when(municipalityRepository.findById(dto.getMunicipalityId())).thenReturn(Optional.of(Municipality.builder().id(dto.getMunicipalityId()).build()));

        Address entity = addressMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getStreetName(), entity.getStreetName());
        assertEquals(dto.getCity(), entity.getCity());
        assertEquals(dto.getZipCode(), entity.getZipCode());
        assertNotNull(entity.getCountry());
        assertEquals(dto.getCountryId(), entity.getCountry().getId());
        assertNotNull(entity.getMunicipality());
        assertEquals(dto.getMunicipalityId(), entity.getMunicipality().getId());
    }

    @Test
    void shouldMapAddressToDto() {
        Address entity = Instancio.create(Address.class);
        AddressDto dto = addressMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getStreetName(), dto.getStreetName());
        assertEquals(entity.getCity(), dto.getCity());
        assertEquals(entity.getCountry().getId(), dto.getCountryId());
        assertEquals(entity.getMunicipality().getId(), dto.getMunicipalityId());
    }
}
