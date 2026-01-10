package com.amachi.app.core.geography.municipio.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.core.geography.municipio.dto.MunicipioDto;
import com.amachi.app.core.geography.municipio.entity.Municipio;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface MunicipioMapper extends EntityDtoMapper<Municipio, MunicipioDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "provincia.id", source = "provinciaId")
    Municipio toEntity(MunicipioDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "provincia.id", source = "provinciaId")
    void updateEntityFromDto(MunicipioDto dto, @MappingTarget Municipio entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    @Mapping(target = "provinciaId", source = "provincia.id")
    MunicipioDto toDto(Municipio entity);
}
