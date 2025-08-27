package com.amachi.app.vitalia.departamentothospital.mapper;

import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.departamentothospital.dto.DepartamentoTipoDto;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoTipo;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface DepartamentoTipoMapper extends EntityDtoMapper<DepartamentoTipo, DepartamentoTipoDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    DepartamentoTipo toEntity(DepartamentoTipoDto dto);

    @Override
    DepartamentoTipoDto toDto(DepartamentoTipo entity);
}