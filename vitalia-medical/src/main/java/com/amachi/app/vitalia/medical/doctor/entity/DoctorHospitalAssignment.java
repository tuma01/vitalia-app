package com.amachi.app.vitalia.medical.doctor.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.domain.hospital.entity.Hospital;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

/**
 * Registro de asignación de facultativos a centros hospitalarios (SaaS Elite Tier).
 * Permite gestionar la itinerancia de los médicos entre distintos edificios o centros.
 */
@Entity
@Table(
    name = "MED_DOCTOR_HOSPITAL_ASSIGN",
    indexes = {
        @Index(name = "IDX_DHA_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_DHA_DOCTOR", columnList = "FK_ID_DOCTOR"),
        @Index(name = "IDX_DHA_HOSPITAL", columnList = "FK_ID_HOSPITAL")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Gestión de vinculación física del médico con centros de salud — SaaS Elite Tier")
public class DoctorHospitalAssignment extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DOCTOR", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_DR_HOSP_ASSIGN_DOCTOR"))
    private Doctor doctor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HOSPITAL", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_DR_HOSP_ASSIGN_HOSPITAL"))
    private Hospital hospital;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "IS_PRIMARY")
    @Builder.Default
    private Boolean isPrimary = false;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.startDate == null) {
            this.startDate = LocalDate.now();
        }
    }
}
