package com.amachi.app.vitalia.medical.consultation.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.history.entity.MedicalHistory;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import com.amachi.app.vitalia.medicalcatalog.consultation.entity.MedicalConsultationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.OffsetDateTime;

/**
 * Entidad Consultation (SaaS Elite Tier).
 * Representa la interacción clínica médica documentada.
 */
@Entity
@Table(
    name = "MED_CONSULTATION",
    indexes = {
        @Index(name = "IDX_CONS_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_CONS_PATIENT", columnList = "FK_ID_PATIENT"),
        @Index(name = "IDX_CONS_DATE", columnList = "VISIT_DATETIME")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Registro de consulta médica — SaaS Elite Tier")
public class Consultation extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_CONS_PATIENT"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DOCTOR", foreignKey = @ForeignKey(name = "FK_MED_CONS_DOCTOR"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_MEDICAL_HISTORY", foreignKey = @ForeignKey(name = "FK_MED_CONS_HISTORY"))
    private MedicalHistory medicalHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_CONSULTATION_TYPE", foreignKey = @ForeignKey(name = "FK_MED_CONS_TYPE"))
    private MedicalConsultationType type;

    @Column(name = "VISIT_DATETIME", nullable = false)
    private OffsetDateTime consultationDate;

    @Column(name = "VISIT_STATUS", nullable = false, length = 50)
    private String status;

    @Column(name = "NOTES", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "TRIAGE_LEVEL", length = 20)
    private String triageLevel;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.status != null) this.status = this.status.trim().toUpperCase();
        if (this.triageLevel != null) this.triageLevel = this.triageLevel.trim().toUpperCase();
    }
}
