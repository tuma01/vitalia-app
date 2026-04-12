package com.amachi.app.vitalia.medical.history.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad Médica de Trazabilidad de Seguros del Paciente.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Audited
@Entity
@Table(
    name = "MED_INSURANCE",
    indexes = {
        @Index(name = "IDX_INS_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_INS_HISTORY", columnList = "FK_ID_MEDICAL_HISTORY"),
        @Index(name = "IDX_INS_PROVIDER", columnList = "FK_ID_HEALTHCARE_PROVIDER")
    }
)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Registro de seguros y coberturas (Pro level)")
public class Insurance extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_MEDICAL_HISTORY", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_INS_HISTORY"))
    private MedicalHistory medicalHistory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_HEALTHCARE_PROVIDER", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_INS_PROVIDER"))
    private HealthcareProvider provider;

    @Column(name = "POLICY_NUMBER", length = 50)
    private String policyNumber;

    @Column(name = "POLICY_TYPE", length = 50)
    private String policyType;

    @Column(name = "EFFECTIVE_DATE")
    private LocalDate effectiveDate;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Column(name = "COVERAGE_DETAILS", length = 1000)
    private String coverageDetails;

    @Column(name = "COPAY_AMOUNT", precision = 12, scale = 2)
    private BigDecimal copayAmount;

    @Column(name = "DEDUCTIBLE_AMOUNT", precision = 12, scale = 2)
    private BigDecimal deductibleAmount;

    @Column(name = "AUTH_REQUIRED")
    @Schema(description = "Indica si el seguro requiere autorización previa para servicios")
    private Boolean requiresAuthorization;

    @Column(name = "IS_CURRENT", nullable = false)
    @Builder.Default
    private Boolean isCurrent = true;

    @Override
    public void delete() {
        this.isDeleted = true;
    }
}
