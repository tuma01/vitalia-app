package com.amachi.app.vitalia.medical.history.entity;
 
import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.common.enums.SleepQuality;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
 
/**
 * Evaluación de funciones biológicas básicas y estilo de vida físico (SaaS Elite Tier).
 * Registro de hábitos sistémicos del paciente.
 */
@Entity
@Table(
    name = "MED_PHYSIOLOGICAL_HABIT",
    indexes = {
        @Index(name = "IDX_HAB_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_HAB_HISTORY", columnList = "FK_ID_MEDICAL_HISTORY")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Evaluación de funciones fisiológicas del paciente (Clinical Tier)")
public class PhysiologicalHabit extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_MEDICAL_HISTORY", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_HAB_FIS_HISTORY"))
    private MedicalHistory medicalHistory;

    @Column(name = "IS_CURRENT", nullable = false)
    @Builder.Default
    private Boolean isCurrent = true;

    @Column(name = "NUTRITION", length = 500)
    private String nutrition;

    @Enumerated(EnumType.STRING)
    @Column(name = "SLEEP_QUALITY", length = 30)
    private SleepQuality sleepQuality;

    @Column(name = "SLEEP_HOURS")
    private Integer sleepHours;

    @Column(name = "URINATION", length = 250)
    private String urination;

    @Column(name = "DEFECATION", length = 250)
    private String defecation;

    @Column(name = "SEXUALITY", length = 250)
    private String sexuality;

    @Column(name = "SPORTS_ACTIVITY", length = 500)
    private String sportsActivity;

    @Column(name = "OTHERS", length = 500)
    private String others;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.nutrition != null) this.nutrition = this.nutrition.trim();
        if (this.others != null) this.others = this.others.trim();
    }
}
