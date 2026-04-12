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
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface PatientMapper extends EntityDtoMapper<Patient, PatientDto> {

    @Override
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "currentBed.id",                 source = "currentBedId")
    @Mapping(target = "currentHospitalization.id",     source = "currentHospitalizationId")
    // Mappings PatientDetails (Relaciones Estructurales)
    @Mapping(target = "patientDetails.bloodType.id",    source = "bloodTypeId")
    @Mapping(target = "patientDetails.weight",          source = "weight")
    @Mapping(target = "patientDetails.height",          source = "height")
    @Mapping(target = "patientDetails.hasDisability",    source = "hasDisability")
    @Mapping(target = "patientDetails.disabilityDetails", source = "disabilityDetails")
    @Mapping(target = "patientDetails.isPregnant",      source = "isPregnant")
    @Mapping(target = "patientDetails.gestationalWeeks", source = "gestationalWeeks")
    @Mapping(target = "patientDetails.childrenCount",    source = "childrenCount")
    @Mapping(target = "patientDetails.ethnicGroup",     source = "ethnicGroup")
    // Mappings EmergencyContact
    @Mapping(target = "emergencyContact.fullName",      source = "emergencyContactName")
    @Mapping(target = "emergencyContact.relationship",  source = "emergencyContactRelationship")
    @Mapping(target = "emergencyContact.phoneNumber",   source = "emergencyContactPhone")
    @Mapping(target = "emergencyContact.email",          source = "emergencyContactEmail")
    // Ignores de Auditoría e Infraestructura
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "address", ignore = true)
    Patient toEntity(PatientDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "currentBedId",                  source = "currentBed.id")
    @Mapping(target = "roomId",                        source = "currentBed.room.id")
    @Mapping(target = "currentHospitalizationId",      source = "currentHospitalization.id")
    @Mapping(target = "address",                       expression = "java(entity.getAddress() != null ? entity.getAddress().toString() : null)")
    // Mappings PatientDetails
    @Mapping(target = "bloodTypeId",                   source = "patientDetails.bloodType.id")
    @Mapping(target = "bloodTypeName",                 source = "patientDetails.bloodType.name")
    @Mapping(target = "hasDisability",                 source = "patientDetails.hasDisability")
    @Mapping(target = "disabilityDetails",              source = "patientDetails.disabilityDetails")
    @Mapping(target = "isPregnant",                    source = "patientDetails.isPregnant")
    @Mapping(target = "gestationalWeeks",               source = "patientDetails.gestationalWeeks")
    @Mapping(target = "childrenCount",                 source = "patientDetails.childrenCount")
    @Mapping(target = "ethnicGroup",                   source = "patientDetails.ethnicGroup")
    // Mappings EmergencyContact
    @Mapping(target = "emergencyContactName",          source = "emergencyContact.fullName")
    @Mapping(target = "emergencyContactRelationship",  source = "emergencyContact.relationship")
    @Mapping(target = "emergencyContactPhone",         source = "emergencyContact.phoneNumber")
    @Mapping(target = "emergencyContactEmail",         source = "emergencyContact.email")
    @Mapping(target = "fullName",                      expression = "java(entity.getFullName())")
    PatientDto toDto(Patient entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "currentBed.id",                 source = "currentBedId")
    @Mapping(target = "currentHospitalization.id",     source = "currentHospitalizationId")
    @Mapping(target = "patientDetails.bloodType.id",    source = "bloodTypeId")
    @Mapping(target = "emergencyContact.fullName",      source = "emergencyContactName")
    @Mapping(target = "emergencyContact.relationship",  source = "emergencyContactRelationship")
    @Mapping(target = "emergencyContact.phoneNumber",   source = "emergencyContactPhone")
    @Mapping(target = "emergencyContact.email",          source = "emergencyContactEmail")
    // Ignores
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "address", ignore = true)
    void updateEntityFromDto(PatientDto dto, @MappingTarget Patient entity);
}
