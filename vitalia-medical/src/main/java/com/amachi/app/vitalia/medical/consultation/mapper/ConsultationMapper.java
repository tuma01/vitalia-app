package com.amachi.app.vitalia.medical.consultation.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.consultation.dto.ConsultationDto;
import com.amachi.app.vitalia.medical.consultation.entity.Consultation;
import org.mapstruct.*;

/**
 * Intelligent mapper for Medical Consultations (SaaS Elite Tier).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ConsultationMapper extends EntityDtoMapper<Consultation, ConsultationDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "medicalHistory.id", source = "medicalHistoryId")
    @Mapping(target = "type.id", source = "typeId")
    Consultation toEntity(ConsultationDto dto);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientFullName", expression = "java(entity.getPatient() != null ? entity.getPatient().getFullName() : null)")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "doctorFullName", expression = "java(entity.getDoctor() != null ? entity.getDoctor().getFullName() : null)")
    @Mapping(target = "medicalHistoryId", source = "medicalHistory.id")
    @Mapping(target = "typeId", source = "type.id")
    ConsultationDto toDto(Consultation entity);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "medicalHistory.id", source = "medicalHistoryId")
    @Mapping(target = "type.id", source = "typeId")
    void updateEntityFromDto(ConsultationDto dto, @MappingTarget Consultation entity);
}
