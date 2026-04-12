package com.amachi.app.vitalia.medical.history.mapper;

import com.amachi.app.core.common.enums.VisitTypeEnum;
import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.common.enums.EncounterType;
import com.amachi.app.vitalia.medical.history.dto.EncounterDto;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import org.mapstruct.*;

/**
 * Enterprise Mapper for Medical Encounters (SaaS Elite Tier).
 * Resolves all unmapped clinical properties and maintains FHIR compliance.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface EncounterMapper extends EntityDtoMapper<Encounter, EncounterDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @Mapping(target = "patient.id",        source = "patientId")
    @Mapping(target = "doctor.id",         source = "doctorId")
    @Mapping(target = "medicalHistory.id",  source = "medicalHistoryId")
    @Mapping(target = "appointment.id",     source = "appointmentId")
    @Mapping(target = "episodeOfCare.id",   source = "episodeOfCareId")
    @Mapping(target = "encounterDate",      source = "encounterDateTime")
    @Mapping(target = "chiefComplaint",     source = "reason")
    @Mapping(target = "clinicalNotes",      source = "notes")
    @Mapping(target = "isDeleted",          ignore = true)
    Encounter toEntity(EncounterDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(source = "patient.id",         target = "patientId")
    @Mapping(source = "patient.fullName",   target = "patientFullName")
    @Mapping(source = "doctor.id",          target = "doctorId")
    @Mapping(source = "doctor.fullName",    target = "doctorFullName")
    @Mapping(source = "appointment.id",     target = "appointmentId")
    @Mapping(source = "medicalHistory.id",  target = "medicalHistoryId")
    @Mapping(source = "episodeOfCare.id",   target = "episodeOfCareId")
    @Mapping(source = "encounterDate",      target = "encounterDateTime")
    @Mapping(source = "chiefComplaint",     target = "reason")
    @Mapping(source = "clinicalNotes",      target = "notes")
    @Mapping(source = "externalId",         target = "externalId")
    EncounterDto toDto(Encounter entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "patient.id",        source = "patientId")
    @Mapping(target = "doctor.id",         source = "doctorId")
    @Mapping(target = "medicalHistory.id",  source = "medicalHistoryId")
    @Mapping(target = "appointment.id",     source = "appointmentId")
    @Mapping(target = "episodeOfCare.id",   source = "episodeOfCareId")
    @Mapping(target = "encounterDate",      source = "encounterDateTime")
    @Mapping(target = "chiefComplaint",     source = "reason")
    @Mapping(target = "clinicalNotes",      source = "notes")
    @Mapping(target = "isDeleted",          ignore = true)
    void updateEntityFromDto(EncounterDto dto, @MappingTarget Encounter entity);

    @ValueMappings({
            @ValueMapping(source = "CONSULTA_EXTERNA",      target = "OUTPATIENT"),
            @ValueMapping(source = "VISITA_EMERGENCIA",     target = "EMERGENCY"),
            @ValueMapping(source = "INGRESO_HOSPITALARIO",   target = "INPATIENT"),
            @ValueMapping(source = "TELECONSULTA",          target = "TELEHEALTH"),
            @ValueMapping(source = "VISITA_ADMINISTRATIVA", target = "ADMINISTRATIVE"),
            @ValueMapping(source = "OTRO",                 target = "OUTPATIENT"),
            @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = "OUTPATIENT")
    })
    EncounterType toEncounterType(VisitTypeEnum encounterType);

    @ValueMappings({
            @ValueMapping(source = "OUTPATIENT",      target = "CONSULTA_EXTERNA"),
            @ValueMapping(source = "EMERGENCY",       target = "VISITA_EMERGENCIA"),
            @ValueMapping(source = "INPATIENT",       target = "INGRESO_HOSPITALARIO"),
            @ValueMapping(source = "TELEHEALTH",      target = "TELECONSULTA"),
            @ValueMapping(source = "ADMINISTRATIVE",  target = "VISITA_ADMINISTRATIVA"),
            @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "OTRO")
    })
    VisitTypeEnum toVisitType(EncounterType encounterType);
}
