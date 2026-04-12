package com.amachi.app.vitalia.medical.history.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

/**
 * Registro individual de patologías hereditarias dentro del núcleo familiar. (SaaS Elite Tier)
 */
@Entity
@Table(
    name = "MED_HEREDITARY_DISEASE",
    indexes = {
        @Index(name = "IDX_HER_DIS_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_HER_DIS_FAM", columnList = "FK_ID_HIST_FAM")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Registro de enfermedad hereditaria (Pro level)")
public class HereditaryDisease extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "NAME", nullable = false, length = 150)
    @Schema(description = "Nombre de la patología hereditaria", example = "Hemofilia A")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_KINSHIP", foreignKey = @ForeignKey(name = "FK_MED_ENF_HERED_KINSHIP"))
    @Schema(description = "Parentesco o vínculo familiar afectado")
    private Kinship kinship;

    @Column(name = "REMARK", length = 500)
    @Schema(description = "Observaciones clínicas de la patología")
    private String remark;

    @Column(name = "DIAGNOSIS_DATE")
    @Schema(description = "Fecha aproximada del diagnóstico familiar")
    private LocalDate diagnosisDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_HIST_FAM", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_ENF_HERED_FAM"))
    private FamilyHistory familyHistory;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.name != null) this.name = this.name.trim();
        if (this.remark != null) this.remark = this.remark.trim();
    }
}
