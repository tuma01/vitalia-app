package com.amachi.app.vitalia.medical.nurse.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.nurse.dto.NurseDto;
import com.amachi.app.vitalia.medical.nurse.entity.Nurse;
import org.mapstruct.*;

/**
 * Mapper profesional para la gestión de perfiles de enfermería (SaaS Elite Tier).
 * Refactorizado para soportar Composición de Identidad Elástica.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface NurseMapper extends EntityDtoMapper<Nurse, NurseDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    // Mapping Identidad (Delegación a Person)
    @Mapping(target = "person.firstName",              source = "firstName")
    @Mapping(target = "person.lastName",               source = "lastName")
    @Mapping(target = "person.nationalId",             source = "nationalId")
    @Mapping(target = "person.gender",                 source = "gender")
    @Mapping(target = "person.birthDate",              source = "birthDate")
    @Mapping(target = "person.email",                  source = "email")
    @Mapping(target = "person.phoneNumber",            source = "phoneNumber")
    
    @Mapping(target = "departmentUnit.id",  source = "departmentUnitId")
    @Mapping(target = "clinicalSkills",     source = "specializedSkills")
    Nurse toEntity(NurseDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    // Mapping Identidad (Consumo desde Person)
    @Mapping(target = "firstName",                     source = "person.firstName")
    @Mapping(target = "lastName",                      source = "person.lastName")
    @Mapping(target = "fullName",                      expression = "java(entity.getPerson() != null ? entity.getPerson().getFullName() : null)")
    @Mapping(target = "nationalId",                    source = "person.nationalId")
    @Mapping(target = "gender",                        expression = "java(entity.getPerson() != null && entity.getPerson().getGender() != null ? entity.getPerson().getGender().name() : null)")
    @Mapping(target = "birthDate",                     source = "person.birthDate")
    @Mapping(target = "email",                         source = "person.email")
    @Mapping(target = "phoneNumber",                   source = "person.phoneNumber")
    
    @Mapping(target = "departmentUnitId",   source = "departmentUnit.id")
    @Mapping(target = "specializedSkills",  source = "clinicalSkills")
    @Mapping(target = "externalId",         source = "externalId")
    NurseDto toDto(Nurse entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "person.firstName",              source = "firstName")
    @Mapping(target = "person.lastName",               source = "lastName")
    @Mapping(target = "person.nationalId",             source = "nationalId")
    
    @Mapping(target = "departmentUnit.id",  source = "departmentUnitId")
    @Mapping(target = "clinicalSkills",     source = "specializedSkills")
    void updateEntityFromDto(NurseDto dto, @MappingTarget Nurse entity);
}
