package com.amachi.app.vitalia.medical.employee.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.employee.dto.EmployeeDto;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface EmployeeMapper extends EntityDtoMapper<Employee, EmployeeDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "user.id", source = "fkIdUser")
    Employee toEntity(EmployeeDto dto);

    @Override
    @Mapping(target = "fkIdUser", source = "user.id")
    EmployeeDto toDto(Employee entity);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user.id", source = "fkIdUser")
    void updateEntityFromDto(EmployeeDto dto, @MappingTarget Employee entity);
}
