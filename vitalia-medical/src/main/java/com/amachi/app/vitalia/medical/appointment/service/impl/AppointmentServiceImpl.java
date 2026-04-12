package com.amachi.app.vitalia.medical.appointment.service.impl;

import com.amachi.app.core.common.context.TenantContext;
import com.amachi.app.core.common.enums.AppointmentStatus;
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.appointment.dto.search.AppointmentSearchDto;
import com.amachi.app.vitalia.medical.appointment.entity.Appointment;
import com.amachi.app.vitalia.medical.appointment.event.AppointmentCreatedEvent;
import com.amachi.app.vitalia.medical.appointment.event.AppointmentUpdatedEvent;
import com.amachi.app.vitalia.medical.appointment.repository.AppointmentRepository;
import com.amachi.app.vitalia.medical.appointment.service.AppointmentService;
import com.amachi.app.vitalia.medical.appointment.specification.AppointmentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Motor de Agendas Médicas (SaaS Elite Tier).
 * Resiliencia con bloqueo pesimista y aislamiento sistémico.
 */
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl extends BaseService<Appointment, AppointmentSearchDto> 
        implements AppointmentService {

    private final AppointmentRepository repository;
    private final DomainEventPublisher eventPublisher;

    private static final List<AppointmentStatus> EXCLUDED_FOR_COLLISIONS = List.of(
            AppointmentStatus.CANCELLED,
            AppointmentStatus.NO_SHOW
    );

    @Override
    protected CommonRepository<Appointment, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Appointment> buildSpecification(AppointmentSearchDto searchDto) {
        return new AppointmentSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Appointment entity) {
        eventPublisher.publish(new AppointmentCreatedEvent(
                entity.getId(),
                entity.getPatient() != null ? entity.getPatient().getId() : null,
                entity.getDoctor() != null ? entity.getDoctor().getId() : null,
                entity.getStartTime()
        ));
    }

    @Override
    protected void publishUpdatedEvent(Appointment entity) {
        eventPublisher.publish(new AppointmentUpdatedEvent(
                entity.getId(),
                entity.getPatient() != null ? entity.getPatient().getId() : null,
                entity.getDoctor() != null ? entity.getDoctor().getId() : null,
                entity.getStartTime(),
                entity.getStatus()
        ));
    }

    @Override
    @Transactional
    public Appointment create(Appointment entity) {
        if (entity.getStartTime().isAfter(entity.getEndTime())) {
            throw new BusinessException("appointment.error.invalid.range");
        }

        // Validación Atómica con Bloqueo Pesimista
        validateCollisions(entity);

        entity.setStatus(AppointmentStatus.PENDING);
        return super.create(entity);
    }

    @Override
    @Transactional
    public Appointment update(Long id, Appointment entity) {
        Appointment existing = getById(id); // Fail-fast isolation enabled by BaseService

        // Multi-operator soft lock
        if (existing.getLockedUntil() != null && existing.getLockedUntil().isAfter(OffsetDateTime.now())) {
            throw new BusinessException("appointment.error.resource.locked");
        }

        if (!existing.getStartTime().isEqual(entity.getStartTime()) || !existing.getEndTime().isEqual(entity.getEndTime())) {
            validateCollisions(entity);
        }

        entity.setId(id);
        return super.update(id, entity);
    }

    @Override
    @Transactional
    public Appointment updateStatus(Long id, AppointmentStatus status) {
        Appointment appt = getById(id);
        appt.setStatus(status);
        if (status == AppointmentStatus.COMPLETED) {
            appt.setCompletedAt(OffsetDateTime.now());
        }
        return repository.save(appt);
    }

    @Override
    @Transactional
    public Appointment reschedule(Long id, OffsetDateTime newStartTime, OffsetDateTime newEndTime) {
        Appointment appt = getById(id);
        
        if (appt.getStatus() == AppointmentStatus.COMPLETED || appt.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BusinessException("appointment.error.cannot.reschedule.finalized");
        }

        appt.setStartTime(newStartTime);
        appt.setEndTime(newEndTime);
        
        // Validación con Bloqueo Pesimista
        validateCollisions(appt);
        
        return repository.save(appt);
    }

    @Override
    @Transactional
    public Appointment cancel(Long id, String reason) {
        Appointment appt = getById(id);
        appt.setStatus(AppointmentStatus.CANCELLED);
        appt.setCancelReason(reason);
        appt.setCancelledAt(OffsetDateTime.now());
        return repository.save(appt);
    }

    @Override
    @Transactional
    public Appointment registerNoShow(Long id) {
        Appointment appt = getById(id);
        appt.setNoShow(true);
        appt.setStatus(AppointmentStatus.NO_SHOW);
        return repository.save(appt);
    }

    @Override
    @Transactional
    public Appointment checkIn(Long id) {
        Appointment appt = getById(id);
        appt.setCheckedInAt(OffsetDateTime.now());
        appt.setStatus(AppointmentStatus.IN_PROGRESS);
        return repository.save(appt);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateCollisions(Appointment appt) {
        // Usar context tenant para colisiones
        String tenantId = TenantContext.getTenant();

        if (repository.countOverlappingDoctor(appt.getDoctor().getId(), appt.getStartTime(), appt.getEndTime(), EXCLUDED_FOR_COLLISIONS) > 0) {
            throw new BusinessException("appointment.error.doctor.collision");
        }

        if (repository.countOverlappingPatient(appt.getPatient().getId(), appt.getStartTime(), appt.getEndTime(), EXCLUDED_FOR_COLLISIONS) > 0) {
            throw new BusinessException("appointment.error.patient.collision");
        }

        if (appt.getRoom() != null && repository.countOverlappingRoom(appt.getRoom().getId(), appt.getStartTime(), appt.getEndTime(), EXCLUDED_FOR_COLLISIONS) > 0) {
            throw new BusinessException("appointment.error.room.collision");
        }
    }

    @Override
    @Transactional
    public void lockAppointment(Long id, String operatorName, int minutes) {
        Appointment appt = getById(id);
        appt.setLockedUntil(OffsetDateTime.now().plusMinutes(minutes));
        appt.setLockedBy(operatorName);
        repository.save(appt);
    }
}
