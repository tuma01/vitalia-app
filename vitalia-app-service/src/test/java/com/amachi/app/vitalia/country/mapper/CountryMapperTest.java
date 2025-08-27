package com.amachi.app.vitalia.country.mapper;

import com.amachi.app.vitalia.country.dto.CountryDto;
import com.amachi.app.vitalia.country.entity.Country;
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
        CountryDto dto = CountryDto.builder()
                .id(1L)
                .name("Country Example")
                .iso("CE")
                .build();
        Country entity = countryMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getIso(), entity.getIso());
    }

    @Test
    void shouldMapEntityToDto() {
        Country entity = Country.builder()
                .id(1L)
                .name("Country Example")
                .iso("CE")
                .build();
        CountryDto dto = countryMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getIso(), dto.getIso());
    }
}