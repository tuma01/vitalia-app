package com.amachi.app.vitalia.provincia.mapper;


import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.departamento.mapper.DepartamentoMapper;
import com.amachi.app.vitalia.provincia.dto.ProvinciaDto;
import com.amachi.app.vitalia.provincia.entity.Provincia;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                DepartamentoMapper.class
        }
)
public interface ProvinciaMapper extends EntityDtoMapper<Provincia, ProvinciaDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    Provincia toEntity(ProvinciaDto dto);

    @Override
    ProvinciaDto toDto(Provincia entity);
}
