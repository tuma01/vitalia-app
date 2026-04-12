package com.amachi.app.vitalia.medical.appointment.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.common.enums.AppointmentStatus;
import com.amachi.app.vitalia.medical.common.enums.AppointmentPriority;
import com.amachi.app.vitalia.medical.common.enums.AppointmentSource;
import com.amachi.app.vitalia.medical.common.enums.AppointmentType;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import com.amachi.app.vitalia.medical.infrastructure.entity.Room;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Appointment (SaaS Elite Tier).
 * Standardized using Core-Geography "Enterprise-Grade" Pattern.
 */
@Entity
@Table(
    name = "MED_APPOINTMENT",
    indexes = {
        @Index(name = "IDX_APP_TIME", columnList = "START_TIME, END_TIME"),
        @Index(name = "IDX_APP_DOCTOR", columnList = "FK_ID_DOCTOR"),
        @Index(name = "IDX_APP_STATUS", columnList = "STATUS"),
        @Index(name = "IDX_APP_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_APP_PATIENT", columnList = "FK_ID_PATIENT")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "SaaS Elite Tier Appointment Implementation")
public class Appointment extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_APP_PATIENT"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_DOCTOR", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_APP_DOCTOR"))
    private Doctor doctor;

    @Column(name = "START_TIME", nullable = false)
    @Schema(description = "ISO-8601 clinical start time", example = "2026-03-25T14:30:00-04:00")
    private OffsetDateTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private OffsetDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_UNIT", foreignKey = @ForeignKey(name = "FK_MED_APP_UNIT"))
    private DepartmentUnit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ROOM", foreignKey = @ForeignKey(name = "FK_MED_APP_ROOM"))
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(name = "APPOINTMENT_TYPE", length = 30)
    private AppointmentType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY", length = 30)
    private AppointmentPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "SOURCE", length = 30)
    private AppointmentSource source;

    @Column(name = "NO_SHOW")
    @Builder.Default
    private Boolean noShow = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ENCOUNTER", foreignKey = @ForeignKey(name = "FK_MED_APP_ENCOUNTER"))
    private Encounter encounter;

    @Column(name = "REASON", length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    private AppointmentStatus status;

    @Column(name = "CHECKED_IN_AT")
    private OffsetDateTime checkedInAt;

    @Column(name = "COMPLETED_AT")
    private OffsetDateTime completedAt;

    @Column(name = "CANCEL_REASON", length = 500)
    private String cancelReason;

    @Column(name = "CANCELLED_AT")
    private OffsetDateTime cancelledAt;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AppointmentReminder> reminders = new ArrayList<>();

    @Column(name = "LOCKED_UNTIL")
    @Schema(description = "Soft-lock for multi-operator concurrency")
    private OffsetDateTime lockedUntil;

    @Column(name = "LOCKED_BY", length = 100)
    private String lockedBy;

    // ===============================
    // 🧠 DOMAIN NORMALIZATION
    // ===============================

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (reason != null) {
            reason = reason.trim();
        }
        if (cancelReason != null) {
            cancelReason = cancelReason.trim();
        }
    }
}
