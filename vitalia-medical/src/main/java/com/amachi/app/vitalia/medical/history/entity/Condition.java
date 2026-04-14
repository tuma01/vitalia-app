package com.amachi.app.vitalia.medical.history.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.common.enums.ClinicalStatus;
import com.amachi.app.vitalia.medical.common.enums.ConditionType;
import com.amachi.app.vitalia.medical.common.enums.Severity;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

/**
 * Representa una condición de salud (Enfermedad, Diagnóstico) según el estándar FHIR Condition.
 * SaaS Elite Tier Standard: Multi-tenant professional isolation.
 */
@Entity
@Table(name = "MED_CONDITION", indexes = {
    @Index(name = "IDX_COND_TENANT", columnList = "TENANT_ID"),
    @Index(name = "IDX_COND_PATIENT", columnList = "FK_ID_PATIENT"),
    @Index(name = "IDX_COND_ICD10", columnList = "FK_ID_ICD10")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Audited
@Schema(description = "Registro unificado de patología/condición clínica (FHIR Condition)")
@EqualsAndHashCode(callSuper = true)
public class Condition extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_COND_PATIENT"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ENCOUNTER", foreignKey = @ForeignKey(name = "FK_MED_COND_ENC"))
    @Schema(description = "Encuentro médico donde se diagnosticó la condición")
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ICD10", foreignKey = @ForeignKey(name = "FK_MED_COND_ICD"))
    @Schema(description = "Referencia codificada CIE-10 (Catálogo Global)")
    private Icd10 icd10;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_DOCTOR", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_COND_DR"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_EPISODE_OF_CARE", foreignKey = @ForeignKey(name = "FK_MED_COND_EPI"))
    private EpisodeOfCare episodeOfCare;

    @Column(name = "DISPLAY_NAME", length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "CLINICAL_STATUS", nullable = false, length = 50)
    private ClinicalStatus clinicalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONDITION_TYPE", nullable = false, length = 50)
    private ConditionType conditionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEVERITY", length = 30)
    private Severity severity;

    @Column(name = "SYMPTOMS", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "TREATMENT_NOTES", columnDefinition = "TEXT")
    private String treatmentNotes;

    @Column(name = "DIAGNOSIS_DATE")
    private LocalDate diagnosisDate;

    @Column(name = "ABATEMENT_DATE")
    private LocalDate abatementDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_MEDICAL_HISTORY", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_COND_HIST"))
    private MedicalHistory medicalHistory;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.name != null) this.name = this.name.trim();
        if (this.symptoms != null) this.symptoms = this.symptoms.trim();
    }
}
