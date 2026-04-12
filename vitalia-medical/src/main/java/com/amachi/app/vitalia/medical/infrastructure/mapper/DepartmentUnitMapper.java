package com.amachi.app.vitalia.medical.infrastructure.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.infrastructure.dto.DepartmentUnitDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import org.mapstruct.*;

/**
 * Mapper para la gestion de misiones de unidades fisicas hospitalarias (SaaS Elite Tier).
 * Maneja las relaciones jerarquicas con tipos de unidad y jefes.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface DepartmentUnitMapper extends EntityDtoMapper<DepartmentUnit, DepartmentUnitDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "unitType.id", source = "unitTypeId")
    @Mapping(target = "headOfEmployee.id", source = "headOfEmployeeId")
    @Mapping(target = "parentUnit.id", source = "parentUnitId")
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "subUnits", ignore = true)
    DepartmentUnit toEntity(DepartmentUnitDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "unitTypeId", source = "unitType.id")
    @Mapping(target = "unitTypeName", source = "unitType.name")
    @Mapping(target = "parentUnitId", source = "parentUnit.id")
    @Mapping(target = "parentUnitName", expression = "java(entity.getParentUnit() != null ? entity.getParentUnit().getName() : null)")
    @Mapping(target = "headOfEmployeeId", source = "headOfEmployee.id")
    @Mapping(target = "headOfEmployeeName", expression = "java(entity.getHeadOfEmployee() != null ? entity.getHeadOfEmployee().getFullName() : null)")
    DepartmentUnitDto toDto(DepartmentUnit entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "unitType.id", source = "unitTypeId")
    @Mapping(target = "headOfEmployee.id", source = "headOfEmployeeId")
    @Mapping(target = "parentUnit.id", source = "parentUnitId")
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "subUnits", ignore = true)
    void updateEntityFromDto(DepartmentUnitDto dto, @MappingTarget DepartmentUnit entity);
}
