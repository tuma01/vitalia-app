package com.amachi.app.vitalia.medical.patient.mapper;

import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.patient.dto.PatientDto;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import org.mapstruct.*;

/**
 * Enterprise Mapper para la gestión de pacientes (SaaS Elite Tier).
 * Diseño limpio sin mapeos manuales redundantes gracias a la armonización de DTOs.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.patient.dto.PatientDto;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import org.mapstruct.*;

/**
 * Enterprise Mapper para la gestión de pacientes (SaaS Elite Tier).
 * Refactorizado para soportar Composición de Identidad Elástica.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface PatientMapper extends EntityDtoMapper<Patient, PatientDto> {

    @Override
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    // Mapping Identidad (Delegación a Person)
    @Mapping(target = "person.firstName",              source = "firstName")
    @Mapping(target = "person.lastName",               source = "lastName")
    @Mapping(target = "person.nationalId",             source = "nationalId")
    @Mapping(target = "person.gender",                 source = "gender")
    @Mapping(target = "person.civilStatus",            source = "civilStatus")
    @Mapping(target = "person.birthDate",              source = "birthDate")
    @Mapping(target = "person.email",                  source = "email")
    @Mapping(target = "person.phoneNumber",            source = "phoneNumber")
    
    @Mapping(target = "currentBed.id",                 source = "currentBedId")
    @Mapping(target = "currentHospitalization.id",     source = "currentHospitalizationId")
    
    // Mappings PatientDetails
    @Mapping(target = "patientDetails.bloodType.id",    source = "bloodTypeId")
    @Mapping(target = "patientDetails.weight",          source = "weight")
    @Mapping(target = "patientDetails.height",          source = "height")
    
    // Mappings EmergencyContact
    @Mapping(target = "emergencyContact.fullName",      source = "emergencyContactName")
    @Mapping(target = "emergencyContact.relationship",  source = "emergencyContactRelationship")
    @Mapping(target = "emergencyContact.phoneNumber",   source = "emergencyContactPhone")
    
    // Ignores de Infraestructura
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    Patient toEntity(PatientDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    // Mapping Identidad (Consumo desde Person)
    @Mapping(target = "firstName",                     source = "person.firstName")
    @Mapping(target = "lastName",                      source = "person.lastName")
    @Mapping(target = "fullName",                      expression = "java(entity.getPerson() != null ? entity.getPerson().getFullName() : null)")
    @Mapping(target = "nationalId",                    source = "person.nationalId")
    @Mapping(target = "gender",                        source = "person.gender")
    @Mapping(target = "civilStatus",                   source = "person.civilStatus")
    @Mapping(target = "birthDate",                     source = "person.birthDate")
    @Mapping(target = "email",                         source = "person.email")
    @Mapping(target = "phoneNumber",                   source = "person.phoneNumber")
    
    @Mapping(target = "currentBedId",                  source = "currentBed.id")
    @Mapping(target = "currentHospitalizationId",      source = "currentHospitalization.id")
    @Mapping(target = "bloodTypeId",                   source = "patientDetails.bloodType.id")
    @Mapping(target = "bloodTypeName",                 source = "patientDetails.bloodType.name")
    
    @Mapping(target = "emergencyContactName",          source = "emergencyContact.fullName")
    @Mapping(target = "emergencyContactRelationship",  source = "emergencyContact.relationship")
    @Mapping(target = "emergencyContactPhone",         source = "emergencyContact.phoneNumber")
    PatientDto toDto(Patient entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "person.firstName",              source = "firstName")
    @Mapping(target = "person.lastName",               source = "lastName")
    @Mapping(target = "person.nationalId",             source = "nationalId")
    @Mapping(target = "person.email",                  source = "email")
    @Mapping(target = "person.phoneNumber",            source = "phoneNumber")
    
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromDto(PatientDto dto, @MappingTarget Patient entity);
}

}
