package com.amachi.app.vitalia.medical.history.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.common.enums.EncounterStatus;
import com.amachi.app.vitalia.medical.common.enums.EncounterType;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import com.amachi.app.vitalia.medical.appointment.entity.Appointment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.OffsetDateTime;

/**
 * Entidad de Encuentro Médico (Encounter - SaaS Elite Tier).
 * Representa una interacción clínica entre un paciente y un profesional.
 */
@Entity
@Table(name = "MED_ENCOUNTER", indexes = {
    @Index(name = "IDX_ENC_TENANT", columnList = "TENANT_ID"),
    @Index(name = "IDX_ENC_STATUS", columnList = "STATUS"),
    @Index(name = "IDX_ENC_DATE", columnList = "ENCOUNTER_DATE"),
    @Index(name = "IDX_ENC_PATIENT", columnList = "FK_ID_PATIENT")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Audited
@Schema(description = "Medical encounter record")
@EqualsAndHashCode(callSuper = true)
public class Encounter extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "ENCOUNTER_DATE", nullable = false)
    @Schema(description = "ISO-8601 clinical check-in time")
    private OffsetDateTime encounterDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ENCOUNTER_TYPE", nullable = false, length = 30)
    private EncounterType encounterType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    private EncounterStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_ENC_PATIENT"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DOCTOR", foreignKey = @ForeignKey(name = "FK_MED_ENC_DOCTOR"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HISTORY", foreignKey = @ForeignKey(name = "FK_MED_ENC_HISTORY"))
    private MedicalHistory medicalHistory;

    @Column(name = "CHIEF_COMPLAINT", length = 500)
    private String chiefComplaint;

    @Column(name = "SYMPTOMS", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "DIAGNOSIS_NOTES", columnDefinition = "TEXT")
    private String diagnosisNotes;

    @Column(name = "TREATMENT_PLAN", columnDefinition = "TEXT")
    private String treatmentPlan;

    @Column(name = "RECOMMENDATIONS", columnDefinition = "TEXT")
    private String recommendations;

    @Column(name = "CLINICAL_NOTES", columnDefinition = "TEXT")
    private String clinicalNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_APPT", foreignKey = @ForeignKey(name = "FK_MED_ENC_APPT"))
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_EPISODE_OF_CARE", foreignKey = @ForeignKey(name = "FK_MED_ENC_EPI"))
    @Schema(description = "Episodio de cuidado al que pertenece este encuentro")
    private EpisodeOfCare episodeOfCare;

    @Column(name = "DURATION_MINUTES")
    private Integer durationMinutes;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    // --- Aliases for clinical consistency (SaaS Expert Tier) ---
    public String getReason() { return this.chiefComplaint; }
    public void setReason(String reason) { this.chiefComplaint = reason; }
    public String getNotes() { return this.clinicalNotes; }
    public void setNotes(String notes) { this.clinicalNotes = notes; }
    public OffsetDateTime getEncounterDateTime() { return this.encounterDate; }
    public void setEncounterDateTime(OffsetDateTime dt) { this.encounterDate = dt; }

    /**
     * Verifica si el encuentro tiene diagnósticos asociados para permitir su cierre.
     */
    public boolean hasDiagnosis() {
        return this.diagnosisNotes != null && !this.diagnosisNotes.isBlank();
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.chiefComplaint != null) this.chiefComplaint = this.chiefComplaint.trim();
        if (this.clinicalNotes != null) this.clinicalNotes = this.clinicalNotes.trim();
        if (this.symptoms != null) this.symptoms = this.symptoms.trim();
        if (this.diagnosisNotes != null) this.diagnosisNotes = this.diagnosisNotes.trim();
        if (this.treatmentPlan != null) this.treatmentPlan = this.treatmentPlan.trim();
        if (this.recommendations != null) this.recommendations = this.recommendations.trim();
        if (this.encounterDate == null) this.encounterDate = OffsetDateTime.now();
    }
}
