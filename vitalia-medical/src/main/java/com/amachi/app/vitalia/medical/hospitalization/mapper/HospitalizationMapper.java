package com.amachi.app.vitalia.medical.hospitalization.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.hospitalization.dto.HospitalizationDto;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;
import org.mapstruct.*;

/**
 * Enterprise Mapper para Hospitalización (SaaS Elite Tier).
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface HospitalizationMapper extends EntityDtoMapper<Hospitalization, HospitalizationDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "encounter.id", source = "encounterId")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "nurse.id", source = "nurseId")
    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "bed.id", source = "bedId")
    @Mapping(target = "isDeleted", ignore = true)
    Hospitalization toEntity(HospitalizationDto dto);

    @Override
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientFullName", source = "patient.fullName")
    @Mapping(target = "encounterId", source = "encounter.id")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "doctorFullName", source = "doctor.fullName")
    @Mapping(target = "nurseId", source = "nurse.id")
    @Mapping(target = "nurseFullName", source = "nurse.fullName")
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "unitName", source = "unit.name")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "roomNumber", source = "room.roomNumber")
    @Mapping(target = "bedId", source = "bed.id")
    @Mapping(target = "bedCode", source = "bed.bedCode")
    @Mapping(target = "externalId", source = "externalId")
    HospitalizationDto toDto(Hospitalization entity);

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "encounter.id", source = "encounterId")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "nurse.id", source = "nurseId")
    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "bed.id", source = "bedId")
    @Mapping(target = "isDeleted", ignore = true)
    void updateEntityFromDto(HospitalizationDto dto, @MappingTarget Hospitalization entity);
}
