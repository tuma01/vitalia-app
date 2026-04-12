package com.amachi.app.vitalia.medical.employee.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.employee.dto.EmployeeDto;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import org.mapstruct.*;

/**
 * Enterprise Mapper para recursos humanos hospitalarios (SaaS Elite Tier).
 * Diseño puro sin sobrecargas manuales gracias a la armonización de modelos.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface EmployeeMapper extends EntityDtoMapper<Employee, EmployeeDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "user.id",           source = "userId")
    @Mapping(target = "departmentUnit.id", source = "departmentUnitId")
    Employee toEntity(EmployeeDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "userId",             source = "user.id")
    @Mapping(target = "departmentUnitId",   source = "departmentUnit.id")
    @Mapping(target = "departmentUnitName", source = "departmentUnit.name")
    @Mapping(target = "fullName",           expression = "java(entity.getFullName())")
    EmployeeDto toDto(Employee entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "user.id",           source = "userId")
    @Mapping(target = "departmentUnit.id", source = "departmentUnitId")
    void updateEntityFromDto(EmployeeDto dto, @MappingTarget Employee entity);
}
