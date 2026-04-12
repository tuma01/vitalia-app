package com.amachi.app.vitalia.medical.patient.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.common.enums.VisitStatusEnum;
import com.amachi.app.core.common.enums.VisitTypeEnum;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Registro de visitas e interacciones médicas (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_PATIENT_VISIT",
    indexes = {
        @Index(name = "IDX_PV_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_PV_PATIENT", columnList = "FK_ID_PATIENT"),
        @Index(name = "IDX_PV_DOCTOR", columnList = "FK_ID_DOCTOR"),
        @Index(name = "IDX_PV_DATE", columnList = "VISIT_DATETIME")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"hospitalizations"})
@Audited
@Schema(description = "Registro de visitas e interacciones médicas — SaaS Elite Tier")
public class PatientVisit extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_VISIT_PATIENT"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DOCTOR", foreignKey = @ForeignKey(name = "FK_MED_VISIT_DOCTOR"))
    private Doctor doctor;

    @Column(name = "VISIT_DATETIME", nullable = false)
    private LocalDateTime visitDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "VISIT_TYPE", nullable = false, length = 50)
    private VisitTypeEnum visitType;

    @Enumerated(EnumType.STRING)
    @Column(name = "VISIT_STATUS", nullable = false, length = 50)
    private VisitStatusEnum visitStatus;

    @Column(name = "NOTES", length = 1000)
    private String notes;

    @Column(name = "APPOINTMENT_REF", length = 100)
    private String appointmentReference;

    @Column(name = "DURATION_MINUTES")
    private Integer durationMinutes;

    @Column(name = "TRIAGE_LEVEL", length = 20)
    private String triageLevel;

    @Column(name = "IS_FOLLOW_UP_REQUIRED")
    @Builder.Default
    private Boolean isFollowUpRequired = false;

    @Column(name = "FOLLOW_UP_DATE")
    private LocalDate followUpDate;

    @OneToMany(mappedBy = "patientVisit", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Hospitalization> hospitalizations = new HashSet<>();

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.appointmentReference != null) this.appointmentReference = this.appointmentReference.trim().toUpperCase();
        if (this.triageLevel != null) this.triageLevel = this.triageLevel.trim().toUpperCase();
    }
}
