package com.amachi.app.core.geography.municipality;

import com.amachi.app.core.geography.municipality.dto.MunicipalityDto;
import com.amachi.app.core.geography.municipality.entity.Municipality;
import com.amachi.app.core.geography.municipality.mapper.MunicipalityMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class MunicipalityMapperTest {

    private MunicipalityMapper municipalityMapper;

    @BeforeEach
    void setUp() {
        municipalityMapper = Mappers.getMapper(MunicipalityMapper.class);
    }

    @Test
    void shouldMapMunicipalityDtoToEntity() {
        MunicipalityDto dto = Instancio.create(MunicipalityDto.class);
        Municipality entity = municipalityMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getPopulation(), entity.getPopulation());
        assertEquals(dto.getMunicipalityCode(), entity.getMunicipalityCode());
        assertNotNull(entity.getProvince());
        assertEquals(dto.getProvinceId(), entity.getProvince().getId());
    }

    @Test
    void shouldMapMunicipalityToDto() {
        Municipality entity = Instancio.create(Municipality.class);
        MunicipalityDto dto = municipalityMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getPopulation(), dto.getPopulation());
        assertEquals(entity.getMunicipalityCode(), dto.getMunicipalityCode());
        assertNotNull(entity.getProvince());
        assertEquals(entity.getProvince().getId(), dto.getProvinceId());
    }
}
