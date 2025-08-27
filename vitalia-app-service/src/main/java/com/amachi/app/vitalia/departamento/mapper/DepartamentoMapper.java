package com.amachi.app.vitalia.departamento.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.departamento.dto.DepartamentoDto;
import com.amachi.app.vitalia.departamento.entity.Departamento;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface DepartamentoMapper extends EntityDtoMapper<Departamento, DepartamentoDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "country.id", source = "countryId")
    Departamento toEntity(DepartamentoDto dto);

    @Override
    @Mapping(target = "countryId", source = "country.id")
    DepartamentoDto toDto(Departamento entity);
}