package com.amachi.app.vitalia.medical.doctor.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

/**
 * Catálogo maestro de especialidades profesionales para médicos (SaaS Elite Tier).
 * Permite categorizar al personal facultativo según su área de competencia técnica.
 */
@Entity
@Table(
    name = "MED_DOCTOR_PROF_SPECIALITY",
    indexes = {
        @Index(name = "IDX_DPS_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_DPS_NAME", columnList = "NAME")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_DPS_TENANT_NAME", columnNames = {"TENANT_ID", "NAME"})
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Catálogo de especialidades médicas — SaaS Elite Tier")
public class DoctorProfessionSpeciality extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "NAME", nullable = false, length = 150)
    private String name;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.name != null) this.name = this.name.trim().toUpperCase();
    }
}
