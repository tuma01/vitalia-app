package com.amachi.app.core.geography.departamento.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.geography.departamento.dto.DepartamentoDto;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface DepartamentoMapper extends EntityDtoMapper<Departamento, DepartamentoDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "country.id", source = "countryId")
    Departamento toEntity(DepartamentoDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "country.id", source = "countryId")
    void updateEntityFromDto(DepartamentoDto dto, @MappingTarget Departamento entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "countryId", source = "country.id")
    DepartamentoDto toDto(Departamento entity);
}