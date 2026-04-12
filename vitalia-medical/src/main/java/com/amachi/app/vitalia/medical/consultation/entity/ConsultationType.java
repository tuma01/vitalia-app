package com.amachi.app.vitalia.medical.consultation.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

/**
 * Clasificación de tipos de consulta médica (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_CONSULTATION_TYPE",
    indexes = {
        @Index(name = "IDX_CONS_TYPE_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_CONS_TYPE_NAME", columnList = "NAME")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_CONS_TYPE_TENANT_NAME", columnNames = {"TENANT_ID", "NAME"})
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Categorización de tipos de consulta — SaaS Elite Tier")
public class ConsultationType extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "NAME", nullable = false, length = 150)
    private String name;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_SPECIALTY", foreignKey = @ForeignKey(name = "FK_MED_CONS_TYPE_SPEC"))
    private MedicalSpecialty specialty;

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
