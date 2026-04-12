package com.amachi.app.vitalia.medical.history.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluación de antecedentes hereditarios y salud del núcleo familiar (SaaS Elite Tier).
 * Alineado con el estándar FHIR FamilyMemberHistory.
 */
@Entity
@Table(
    name = "MED_FAMILY_HISTORY",
    indexes = {
        @Index(name = "IDX_FAM_HIST_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_FAM_HIST_HISTORY", columnList = "FK_ID_MEDICAL_HISTORY")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"hereditaryDiseases"})
@Audited
@Schema(description = "Evaluación de antecedentes familiares (Clinical Tier)")
public class FamilyHistory extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_MEDICAL_HISTORY", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_HIST_FAM_HISTORY"))
    private MedicalHistory medicalHistory;

    @Column(name = "IS_CURRENT", nullable = false)
    @Builder.Default
    private Boolean isCurrent = true;

    @Column(name = "MOTHER_HEALTH_INFO", length = 300)
    private String motherHealthInfo;

    @Column(name = "FATHER_HEALTH_INFO", length = 300)
    private String fatherHealthInfo;

    @Column(name = "CARDIAC_FAMHISTORY", length = 300)
    private String cardiacFamilyHistory;

    @Column(name = "MENTAL_FAMHISTORY", length = 300)
    private String mentalFamilyHistory;

    @Column(name = "DIABETES_FAMHISTORY", length = 300)
    private String diabetesFamilyHistory;

    @Column(name = "FAMILY_ETHNICITY", length = 100)
    private String familyEthnicity;

    @Column(name = "GENETIC_RISK_INDEX", length = 30)
    private String geneticRiskIndex;

    @Column(name = "ADDITIONAL_NOTES", columnDefinition = "TEXT")
    private String additionalNotes;

    @OneToMany(mappedBy = "familyHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HereditaryDisease> hereditaryDiseases = new ArrayList<>();

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.familyEthnicity != null) this.familyEthnicity = this.familyEthnicity.trim().toUpperCase();
        if (this.geneticRiskIndex != null) this.geneticRiskIndex = this.geneticRiskIndex.trim().toUpperCase();
        if (this.additionalNotes != null) this.additionalNotes = this.additionalNotes.trim();
    }
}
