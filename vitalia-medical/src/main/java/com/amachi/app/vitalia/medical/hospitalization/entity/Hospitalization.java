package com.amachi.app.vitalia.medical.hospitalization.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.common.enums.AdmissionType;
import com.amachi.app.vitalia.medical.common.enums.DischargeStatus;
import com.amachi.app.vitalia.medical.common.enums.HospitalizationPriority;
import com.amachi.app.vitalia.medical.common.enums.HospitalizationStatus;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.vitalia.medical.infrastructure.entity.Bed;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import com.amachi.app.vitalia.medical.infrastructure.entity.Room;
import com.amachi.app.vitalia.medical.nurse.entity.Nurse;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;

/**
 * Entidad canonical de Hospitalización (SaaS Elite Tier).
 * Maneja el ciclo de vida del paciente internado.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "MED_HOSPITALIZATION",
    indexes = {
        @Index(name = "IDX_HOSP_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_HOSP_PATIENT", columnList = "FK_ID_PATIENT"),
        @Index(name = "IDX_HOSP_STATUS", columnList = "HOSPITALIZATION_STATUS"),
        @Index(name = "IDX_HOSP_DATE", columnList = "ADMISSION_DATE")
    }
)
@Audited
@Schema(description = "Hospital inpatient episode — SaaS Elite Tier")
@EqualsAndHashCode(callSuper = true)
public class Hospitalization extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ENCOUNTER", foreignKey = @ForeignKey(name = "FK_MED_HOSP_ENCOUNTER"))
    @Schema(description = "Clinical encounter that triggered this hospitalization")
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_HOSP_PATIENT"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_UNIT", foreignKey = @ForeignKey(name = "FK_MED_HOSP_UNIT"))
    private DepartmentUnit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ROOM", foreignKey = @ForeignKey(name = "FK_MED_HOSP_ROOM"))
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_BED", foreignKey = @ForeignKey(name = "FK_MED_HOSP_BED"))
    private Bed bed;

    @Column(name = "ADMISSION_DATE", nullable = false)
    private OffsetDateTime admissionDate;

    @Column(name = "DISCHARGE_DATE")
    private OffsetDateTime dischargeDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "HOSPITALIZATION_STATUS", nullable = false, length = 50)
    private HospitalizationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "HOSPITALIZATION_PRIORITY", length = 30)
    private HospitalizationPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "DISCHARGE_STATUS", length = 30)
    private DischargeStatus dischargeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DR_RESP", foreignKey = @ForeignKey(name = "FK_MED_HOSP_DR_RESP"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_NS_RESP", foreignKey = @ForeignKey(name = "FK_MED_HOSP_NS_RESP"))
    private Nurse nurse;

    @Column(name = "ADMISSION_REASON", length = 1000)
    private String admissionReason;

    @Column(name = "DISCHARGE_REASON", length = 1000)
    private String dischargeReason;

    @Column(name = "DIAGNOSIS_NOTES", columnDefinition = "TEXT")
    private String clinicalNotes;

    @Column(name = "ADMISSION_DIAGNOSIS", length = 500)
    private String admissionDiagnosis;

    @Column(name = "FINAL_DIAGNOSIS", length = 500)
    private String finalDiagnosis;

    @Column(name = "ESTIMATED_DISCHARGE_DATE")
    private OffsetDateTime estimatedDischargeDate;

    @Column(name = "FOLLOW_UP_DATE")
    private java.time.LocalDate followUpDate;

    @Column(name = "INSURANCE_AUTH_NUMBER", length = 100)
    private String insuranceAuthorizationNumber;

    @Column(name = "CURRENCY", length = 10)
    @Builder.Default
    private String currency = "USD";

    @Column(name = "TREATMENT_PLAN", columnDefinition = "TEXT")
    private String treatmentPlan;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADMISSION_TYPE", length = 50)
    private AdmissionType admissionType;

    @Column(name = "TOTAL_COST", precision = 12, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "DISCHARGE_INSTRUCTIONS", columnDefinition = "TEXT")
    private String dischargeInstructions;

    @Column(name = "OBSERVATIONS", columnDefinition = "TEXT")
    private String observations;

    @Transient
    public long getLengthOfStayInDays() {
        if (this.admissionDate == null) return 0;
        OffsetDateTime endDate = (this.dischargeDate != null) ? this.dischargeDate : OffsetDateTime.now();
        return Duration.between(this.admissionDate, endDate).toDays();
    }

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.admissionReason != null) this.admissionReason = this.admissionReason.trim();
        if (this.admissionDiagnosis != null) this.admissionDiagnosis = this.admissionDiagnosis.trim();
        if (this.admissionDate == null) this.admissionDate = OffsetDateTime.now();
    }
}
