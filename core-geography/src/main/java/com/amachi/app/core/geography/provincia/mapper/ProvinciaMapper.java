package com.amachi.app.core.geography.provincia.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.geography.provincia.dto.ProvinciaDto;
import com.amachi.app.core.geography.provincia.entity.Provincia;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ProvinciaMapper extends EntityDtoMapper<Provincia, ProvinciaDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "departamento.id", source = "departamentoId")
    Provincia toEntity(ProvinciaDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "departamento.id", source = "departamentoId")
    void updateEntityFromDto(ProvinciaDto dto, @MappingTarget Provincia entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    @Mapping(target = "departamentoId", source = "departamento.id")
    ProvinciaDto toDto(Provincia entity);
}
