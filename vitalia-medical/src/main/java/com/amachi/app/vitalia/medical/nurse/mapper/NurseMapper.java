package com.amachi.app.vitalia.medical.nurse.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.nurse.dto.NurseDto;
import com.amachi.app.vitalia.medical.nurse.entity.Nurse;
import org.mapstruct.*;

/**
 * Mapper profesional para la gestión de perfiles de enfermería (SaaS Elite Tier).
 * Orquestra la identidad desde Person y las métricas operativas de Nurse.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface NurseMapper extends EntityDtoMapper<Nurse, NurseDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "employee.id", source = "employeeId")
    @Mapping(target = "departmentUnit.id", source = "departmentUnitId")
    @Mapping(target = "nationalId", source = "idCard")
    @Mapping(target = "clinicalSkills", source = "specializedSkills")
    Nurse toEntity(NurseDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "idCard", source = "nationalId")
    @Mapping(target = "departmentUnitId", source = "departmentUnit.id")
    @Mapping(target = "specializedSkills", source = "clinicalSkills")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "externalId", source = "externalId")
    NurseDto toDto(Nurse entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "employee.id", source = "employeeId")
    @Mapping(target = "departmentUnit.id", source = "departmentUnitId")
    @Mapping(target = "nationalId", source = "idCard")
    @Mapping(target = "clinicalSkills", source = "specializedSkills")
    void updateEntityFromDto(NurseDto dto, @MappingTarget Nurse entity);
}
