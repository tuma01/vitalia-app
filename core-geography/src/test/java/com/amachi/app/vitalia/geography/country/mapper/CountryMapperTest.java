package com.amachi.app.core.geography.country.mapper;

import com.amachi.app.core.geography.country.dto.CountryDto;
import com.amachi.app.core.geography.country.entity.Country;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CountryMapperTest {
    private CountryMapper countryMapper;

    @BeforeEach
    void setUp() {
        countryMapper = Mappers.getMapper(CountryMapper.class);
    }

    @Test
    void shouldMapDtoToEntity() {
        CountryDto dto = Instancio.create(CountryDto.class);
        Country entity = countryMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getIso(), entity.getIso());
    }

    @Test
    void shouldMapEntityToDto() {
        Country entity = Instancio.create(Country.class);;
        CountryDto dto = countryMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getIso(), dto.getIso());
    }
}