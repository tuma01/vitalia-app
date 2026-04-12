package com.amachi.app.vitalia.medical.history.service.impl;

import com.amachi.app.core.common.enums.AppointmentStatus;
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.appointment.entity.Appointment;
import com.amachi.app.vitalia.medical.appointment.service.AppointmentService;
import com.amachi.app.vitalia.medical.common.enums.EncounterStatus;
import com.amachi.app.vitalia.medical.common.enums.EncounterType;
import com.amachi.app.vitalia.medical.history.dto.request.ConditionRequest;
import com.amachi.app.vitalia.medical.history.dto.request.ObservationRequest;
import com.amachi.app.vitalia.medical.history.dto.request.StartEncounterRequest;
import com.amachi.app.vitalia.medical.history.dto.search.EncounterSearchDto;
import com.amachi.app.vitalia.medical.history.entity.*;
import com.amachi.app.vitalia.medical.history.repository.ConditionRepository;
import com.amachi.app.vitalia.medical.history.repository.EncounterRepository;
import com.amachi.app.vitalia.medical.history.repository.MedicationRequestRepository;
import com.amachi.app.vitalia.medical.history.repository.ObservationRepository;
import com.amachi.app.vitalia.medical.history.service.EncounterService;
import com.amachi.app.vitalia.medical.history.specification.EncounterSpecification;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.repository.Icd10Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

/**
 * Enterprise Clinical Orchestrator (SaaS Elite Tier).
 * Manages clinical encounters with FHIR-compliant integrity and multi-tenant isolation.
 */
@Service
@RequiredArgsConstructor
public class EncounterServiceImpl extends BaseService<Encounter, EncounterSearchDto> implements EncounterService {

    private final EncounterRepository repository;
    private final ConditionRepository conditionRepository;
    private final ObservationRepository observationRepository;
    private final MedicationRequestRepository medicationRequestRepository;
    private final Icd10Repository icd10Repository;
    private final AppointmentService appointmentService;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Encounter, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Encounter> buildSpecification(EncounterSearchDto searchDto) {
        return new EncounterSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Encounter entity) {
        // Placeholder para publicación de eventos de dominio (SaaS Elite)
    }

    @Override
    protected void publishUpdatedEvent(Encounter entity) {
        // Placeholder para publicación de eventos de dominio (SaaS Elite)
    }

    @Override
    @Transactional
    public Encounter startEncounter(StartEncounterRequest request) {
        Appointment appointment = appointmentService.getById(request.getAppointmentId());

        if (appointment.getCheckedInAt() == null) {
            throw new BusinessException("encounter.error.patient.not.checked.in");
        }

        if (repository.existsByAppointmentIdAndStatus(appointment.getId(), EncounterStatus.IN_PROGRESS)) {
            throw new BusinessException("encounter.error.already.in.progress");
        }

        Encounter encounter = Encounter.builder()
                .patient(appointment.getPatient())
                .doctor(appointment.getDoctor())
                .appointment(appointment)
                .medicalHistory(appointment.getPatient().getMedicalHistory())
                .encounterType(EncounterType.OUTPATIENT)
                .encounterDate(OffsetDateTime.now())
                .status(EncounterStatus.IN_PROGRESS)
                .chiefComplaint(appointment.getReason()) 
                .clinicalNotes(request.getStartNote())
                .build();

        return create(encounter);
    }

    @Override
    @Transactional
    public Encounter resumeEncounter(Long encounterId) {
        Encounter encounter = getById(encounterId);
        if (encounter.getStatus() != EncounterStatus.ON_HOLD) {
            throw new BusinessException("encounter.error.not.on.hold");
        }
        encounter.setStatus(EncounterStatus.IN_PROGRESS);
        return update(encounter.getId(), encounter);
    }

    @Override
    @Transactional
    public Encounter holdEncounter(Long encounterId, String reason) {
        Encounter encounter = getById(encounterId);
        if (encounter.getStatus() != EncounterStatus.IN_PROGRESS) {
            throw new BusinessException("encounter.error.must.be.in.progress.to.hold");
        }
        encounter.setStatus(EncounterStatus.ON_HOLD);
        encounter.setNotes(encounter.getNotes() + " | PAUSE: " + reason);
        return update(encounter.getId(), encounter);
    }

    @Override
    @Transactional
    public Encounter completeEncounter(Long encounterId) {
        Encounter encounter = getById(encounterId);

        if (!encounter.hasDiagnosis()) {
            throw new BusinessException("encounter.error.diagnosis.required.to.complete");
        }

        encounter.setStatus(EncounterStatus.COMPLETED);
        encounter.setDurationMinutes((int) java.time.Duration.between(encounter.getEncounterDateTime(), OffsetDateTime.now()).toMinutes());

        if (encounter.getAppointment() != null) {
            Appointment appt = encounter.getAppointment();
            appt.setStatus(AppointmentStatus.COMPLETED);
            appt.setCompletedAt(OffsetDateTime.now());
            appointmentService.update(appt.getId(), appt);
        }

        return update(encounter.getId(), encounter);
    }

    @Override
    @Transactional
    public Encounter cancelEncounter(Long encounterId, String reason) {
        Encounter encounter = getById(encounterId);
        if (encounter.getStatus() == EncounterStatus.COMPLETED) {
            throw new BusinessException("encounter.error.cannot.cancel.completed");
        }
        encounter.setStatus(EncounterStatus.CANCELLED);
        encounter.setNotes(encounter.getNotes() + " | CANCEL: " + reason);
        return update(encounter.getId(), encounter);
    }

    @Override
    @Transactional
    public Condition addCondition(Long encounterId, ConditionRequest request) {
        Encounter encounter = getById(encounterId);
        if (encounter.getStatus() != EncounterStatus.IN_PROGRESS) {
            throw new BusinessException("encounter.error.must.be.in.progress");
        }

        Icd10 icd10 = icd10Repository.findById(request.getIcd10Id())
                .orElseThrow(() -> new ResourceNotFoundException("Icd10", "error.catalog.not.found", request.getIcd10Id()));

        Condition condition = Condition.builder()
                .patient(encounter.getPatient())
                .encounter(encounter)
                .practitioner(encounter.getDoctor())
                .medicalHistory(encounter.getMedicalHistory())
                .icd10(icd10)
                .clinicalStatus(request.getClinicalStatus())
                .conditionType(request.getConditionType())
                .severity(request.getSeverity())
                .symptoms(request.getSymptoms())
                .treatmentNotes(request.getTreatmentNotes())
                .diagnosisDate(java.time.LocalDate.now())
                .build();

        return conditionRepository.save(condition);
    }

    @Override
    @Transactional
    public Observation addObservation(Long encounterId, ObservationRequest request) {
        Encounter encounter = getById(encounterId);
        if (encounter.getStatus() != EncounterStatus.IN_PROGRESS) {
            throw new BusinessException("encounter.error.must.be.in.progress");
        }

        Observation observation = Observation.builder()
                .patient(encounter.getPatient())
                .encounter(encounter)
                .practitioner(encounter.getDoctor())
                .code(request.getCode())
                .name(request.getName())
                .value(request.getValue())
                .unit(request.getUnit())
                .interpretation(request.getInterpretation())
                .status(com.amachi.app.vitalia.medical.common.enums.ObservationStatus.FINAL)
                .effectiveDateTime(OffsetDateTime.now())
                .notes(request.getNotes())
                .build();

        return observationRepository.save(observation);
    }

    @Override
    @Transactional
    public MedicationRequest prescribeMedication(Long encounterId, MedicationRequest medicationRequest) {
        Encounter encounter = getById(encounterId);
        if (encounter.getStatus() != EncounterStatus.IN_PROGRESS) {
            throw new BusinessException("encounter.error.must.be.in.progress");
        }
        medicationRequest.setEncounter(encounter);
        medicationRequest.setPatient(encounter.getPatient());
        medicationRequest.setPractitioner(encounter.getDoctor());
        medicationRequest.setAuthoredOn(OffsetDateTime.now());
        return medicationRequestRepository.save(medicationRequest);
    }
}
