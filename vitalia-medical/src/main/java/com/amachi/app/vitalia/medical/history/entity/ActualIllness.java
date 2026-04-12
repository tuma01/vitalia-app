package com.amachi.app.vitalia.medical.history.entity;
import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

/**
 * Registro ejecutivo de enfermedades activas detectadas en el paciente. (SaaS Elite Tier)
 * Permite el seguimiento terapéutico y la trazabilidad de síntomas actuales.
 */
@Entity
@Table(
    name = "MED_ACTUAL_ILLNESS",
    indexes = {
        @Index(name = "IDX_ACT_ILL_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_ACT_ILL_HISTORY", columnList = "FK_ID_MEDICAL_HISTORY")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Registro de patología activa del expediente clínico")
public class ActualIllness extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Size(max = 150)
    @Column(name = "DISEASE_NAME", nullable = false)
    @Schema(description = "Nombre de la enfermedad", example = "Hipertensión Arterial")
    private String name;

    @PastOrPresent
    @Column(name = "DIAGNOSIS_DATE")
    @Schema(description = "Fecha de diagnóstico")
    private LocalDate diagnosisDate;

    @Column(name = "SYMPTOMS", length = 500)
    @Schema(description = "Sintomatología asociada")
    private String symptoms;

    @Column(name = "TREATMENTS", length = 500)
    @Schema(description = "Tratamientos previos")
    private String treatments;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_MEDICAL_HISTORY", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_ENF_ACTUAL_HISTORY"))
    @Schema(description = "Vínculo al expediente clínico")
    private MedicalHistory medicalHistory;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.name != null) this.name = this.name.trim();
        if (this.symptoms != null) this.symptoms = this.symptoms.trim();
        if (this.treatments != null) this.treatments = this.treatments.trim();
    }
}
