package com.amachi.app.vitalia.medical.infrastructure.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.infrastructure.dto.RoomDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Room;
import org.mapstruct.*;

/**
 * Mapper para la gestion de habitaciones y boxes (SaaS Elite Tier).
 * Vincula la habitacion con su unidad operativa y maneja la normalizacion de identificadores.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface RoomMapper extends EntityDtoMapper<Room, RoomDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "beds", ignore = true)
    @Mapping(target = "hospitalizations", ignore = true)
    Room toEntity(RoomDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "unitName", source = "unit.name")
    @Mapping(target = "externalId", source = "externalId")
    RoomDto toDto(Room entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "beds", ignore = true)
    @Mapping(target = "hospitalizations", ignore = true)
    void updateEntityFromDto(RoomDto dto, @MappingTarget Room entity);
}
