package com.amachi.app.vitalia.medical.appointment.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medical.appointment.dto.AppointmentDto;
import com.amachi.app.vitalia.medical.appointment.entity.Appointment;
import org.mapstruct.*;

/**
 * Mapper inteligente para agendas medicas.
 */
@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface AppointmentMapper extends EntityDtoMapper<Appointment, AppointmentDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "encounter.id", source = "visitId")
    @Mapping(target = "reminders", ignore = true)
    @Mapping(target = "lockedUntil", ignore = true)  // Solo escritura via lockAppointment()
    @Mapping(target = "lockedBy", ignore = true)      // Solo escritura via lockAppointment()
    Appointment toEntity(AppointmentDto dto);

    @Override
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientFullName", expression = "java(entity.getPatient() != null ? entity.getPatient().getFirstName() + \" \" + (entity.getPatient().getLastName() != null ? entity.getPatient().getLastName() : \"\") : null)")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "doctorFullName", expression = "java(entity.getDoctor() != null ? entity.getDoctor().getFirstName() + \" \" + (entity.getDoctor().getLastName() != null ? entity.getDoctor().getLastName() : \"\") : null)")
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "unitName", source = "unit.name")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "roomNumber", source = "room.roomNumber")
    @Mapping(target = "visitId", source = "encounter.id")
    AppointmentDto toDto(Appointment entity);

    @AuditableIgnoreConfig.IgnoreTenantAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "encounter.id", source = "visitId")
    @Mapping(target = "reminders", ignore = true)
    @Mapping(target = "lockedUntil", ignore = true)  // Solo escritura via lockAppointment()
    @Mapping(target = "lockedBy", ignore = true)      // Solo escritura via lockAppointment()
    void updateEntityFromDto(AppointmentDto dto, @MappingTarget Appointment entity);
}
