package com.amachi.app.vitalia.medical.doctor.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.doctor.dto.DoctorDto;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import org.mapstruct.*;

/**
 * Enterprise Mapper para la gestión de facultativos (SaaS Elite Tier).
 * Refactorizado para soportar Composición de Identidad Elástica.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface DoctorMapper extends EntityDtoMapper<Doctor, DoctorDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    // Mapping Identidad (Delegación a Person)
    @Mapping(target = "person.firstName",              source = "firstName")
    @Mapping(target = "person.lastName",               source = "lastName")
    @Mapping(target = "person.nationalId",             source = "nationalId")
    @Mapping(target = "person.gender",                 source = "gender")
    @Mapping(target = "person.birthDate",              source = "birthDate")
    @Mapping(target = "person.email",                  source = "email")
    @Mapping(target = "person.phoneNumber",            source = "phoneNumber")
    
    @Mapping(target = "employee.id",        source = "employeeId")
    @Mapping(target = "departmentUnit.id",  source = "departmentUnitId")
    @Mapping(target = "specialties",        source = "specialtyIds")
    @Mapping(target = "assignments",        ignore = true)
    @Mapping(target = "medicalHistories",   ignore = true)
    @Mapping(target = "encounters",         ignore = true)
    @Mapping(target = "professionalInfos",  ignore = true)
    @Mapping(target = "profile",            ignore = true)
    @Mapping(target = "isDeleted",          ignore = true)
    Doctor toEntity(DoctorDto dto);

    @Override
    // Mapping Identidad (Consumo desde Person)
    @Mapping(target = "firstName",                     source = "person.firstName")
    @Mapping(target = "lastName",                      source = "person.lastName")
    @Mapping(target = "fullName",                      expression = "java(entity.getPerson() != null ? entity.getPerson().getFullName() : null)")
    @Mapping(target = "nationalId",                    source = "person.nationalId")
    @Mapping(target = "gender",                        expression = "java(entity.getPerson() != null && entity.getPerson().getGender() != null ? entity.getPerson().getGender().name() : null)")
    @Mapping(target = "birthDate",                     source = "person.birthDate")
    @Mapping(target = "email",                         source = "person.email")
    @Mapping(target = "phoneNumber",                   source = "person.phoneNumber")
    
    @Mapping(target = "employeeId",         source = "employee.id")
    @Mapping(target = "departmentUnitId",   source = "departmentUnit.id")
    @Mapping(target = "departmentUnitName", source = "departmentUnit.name")
    @Mapping(target = "specialtyIds",       source = "specialties")
    @Mapping(target = "externalId",         source = "externalId")
    DoctorDto toDto(Doctor entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "person.firstName",              source = "firstName")
    @Mapping(target = "person.lastName",               source = "lastName")
    @Mapping(target = "person.nationalId",             source = "nationalId")
    
    @Mapping(target = "employee.id",        source = "employeeId")
    @Mapping(target = "departmentUnit.id",  source = "departmentUnitId")
    @Mapping(target = "specialties",        source = "specialtyIds")
    @Mapping(target = "assignments",        ignore = true)
    @Mapping(target = "medicalHistories",   ignore = true)
    @Mapping(target = "encounters",         ignore = true)
    @Mapping(target = "professionalInfos",  ignore = true)
    @Mapping(target = "profile",            ignore = true)
    @Mapping(target = "isDeleted",          ignore = true)
    void updateEntityFromDto(DoctorDto dto, @MappingTarget Doctor entity);

    default Long mapSpecialtyToId(com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty specialty) {
        return specialty != null ? specialty.getId() : null;
    }

    default com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty mapIdToSpecialty(Long id) {
        if (id == null) return null;
        return com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty.builder().id(id).build();
    }
}
