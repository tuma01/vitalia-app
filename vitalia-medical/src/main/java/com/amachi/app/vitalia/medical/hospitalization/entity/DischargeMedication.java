package com.amachi.app.vitalia.medical.hospitalization.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
 
/**
 * Medicamentos recetados formalmente al momento del alta hospitalaria (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_DISCHARGE_MEDICATION",
    indexes = {
        @Index(name = "IDX_DIS_MED_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_DIS_MED_HOSP", columnList = "FK_ID_HOSPITALIZATION")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Plan de medicación post-hospitalaria (Pro level)")
public class DischargeMedication extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_HOSPITALIZATION", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_DIS_MED_HOSP"))
    private Hospitalization hospitalization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_MEDICATION", foreignKey = @ForeignKey(name = "FK_MED_DIS_MED_CAT"))
    @Schema(description = "Referencia al catálogo maestro de medicamentos")
    private Medication medication;

    @Column(name = "MEDICATION_NAME_DISPLAY", length = 250)
    @Schema(description = "Nombre comercial o presentación específica")
    private String medicationName;

    @Column(name = "DOSAGE", length = 100)
    @Schema(description = "Dosis (Ej: 500mg)")
    private String dosage;

    @Column(name = "FREQUENCY", length = 100)
    @Schema(description = "Frecuencia (Ej: Cada 8 horas)")
    private String frequency;

    @Column(name = "DURATION", length = 100)
    @Schema(description = "Duración del tratamiento (Ej: 7 días)")
    private String duration;

    @Column(name = "INSTRUCTIONS", columnDefinition = "TEXT")
    private String instructions;

    @Override
    public void delete() {
        this.isDeleted = true;
    }
}
