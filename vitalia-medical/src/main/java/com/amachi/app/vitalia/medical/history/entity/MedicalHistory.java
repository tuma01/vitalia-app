package com.amachi.app.vitalia.medical.history.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Medical History (SaaS Elite Tier).
 * Centraliza los registros de salud longitudinales de un paciente bajo un tenant.
 */
@Entity
@Table(name = "MED_MEDICAL_HISTORY", indexes = {
    @Index(name = "IDX_HIST_TENANT", columnList = "TENANT_ID"),
    @Index(name = "IDX_HIST_PATIENT", columnList = "FK_ID_PATIENT"),
    @Index(name = "IDX_HIST_NUMBER", columnList = "HISTORY_NUMBER")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Audited
@Schema(description = "Centralized medical record registry — Core Medical")
@EqualsAndHashCode(callSuper = true, exclude = {"encounters"})
public class MedicalHistory extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "HISTORY_NUMBER", nullable = false, length = 50)
    private String historyNumber;

    @Column(name = "DOCUMENT_UUID", length = 36)
    private String documentUuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_HIST_PATIENT"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PERSON", foreignKey = @ForeignKey(name = "FK_MED_HIST_PERSON"))
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DR_RESP", foreignKey = @ForeignKey(name = "FK_MED_HIST_DR_RESP"))
    private Doctor responsibleDoctor;

    @Column(name = "RECORD_DATE", nullable = false)
    private LocalDate recordDate;

    @Column(name = "VALID_UNTIL")
    private LocalDate validUntil;

    @Column(name = "IS_CURRENT")
    @Builder.Default
    private Boolean isCurrent = true;

    @Column(name = "RECORD_VERSION")
    @Builder.Default
    private Integer recordVersion = 1;

    @Column(name = "STATUS", length = 30)
    @Builder.Default
    private String status = "ACTIVE";

    @Column(name = "CONFIDENTIALITY_LEVEL", length = 30)
    @Builder.Default
    private String confidentialityLevel = "NORMAL";

    @Column(name = "IS_LOCKED")
    @Builder.Default
    private Boolean isLocked = false;

    @Column(name = "IS_ORGAN_DONOR")
    private Boolean isOrganDonor;

    @Column(name = "OBSERVATIONS", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "NOTES", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "medicalHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Encounter> encounters = new ArrayList<>();

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.historyNumber != null) this.historyNumber = this.historyNumber.trim().toUpperCase();
        if (this.observations != null) this.observations = this.observations.trim();
        if (this.notes != null) this.notes = this.notes.trim();
    }
}
