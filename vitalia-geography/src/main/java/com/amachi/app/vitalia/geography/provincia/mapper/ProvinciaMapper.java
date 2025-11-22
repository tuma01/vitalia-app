package com.amachi.app.vitalia.geography.provincia.mapper;

import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.geography.provincia.dto.ProvinciaDto;
import com.amachi.app.vitalia.geography.provincia.entity.Provincia;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface ProvinciaMapper extends EntityDtoMapper<Provincia, ProvinciaDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "departamento.id", source = "departamentoId")
    Provincia toEntity(ProvinciaDto dto);

    @Override
    @Mapping(target = "departamentoId", source = "departamento.id")
    ProvinciaDto toDto(Provincia entity);
}
