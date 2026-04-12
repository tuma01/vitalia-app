package com.amachi.app.vitalia.medical.history.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

/**
 * Registro histórico descriptivo de enfermedades y antecedentes clínicos (SaaS Elite Tier).
 * Permite documentar patologías crónicas que no están en fase aguda pero son relevantes.
 */
@Entity
@Table(
    name = "MED_PAST_ILLNESS",
    indexes = {
        @Index(name = "IDX_PAST_ILL_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_PAST_ILL_HISTORY", columnList = "FK_ID_HIST_MED")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Registro detallado de patología crónica o histórica — SaaS Elite Tier")
public class PastIllness extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Size(max = 150)
    @Column(name = "DISEASE_NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "SYMPTOMS", length = 500)
    private String symptoms;

    @Column(name = "TREATMENTS", length = 500)
    private String treatments;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_HIST_MED", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_REG_ENF_HISTORY"))
    private MedicalHistory medicalHistory;

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
