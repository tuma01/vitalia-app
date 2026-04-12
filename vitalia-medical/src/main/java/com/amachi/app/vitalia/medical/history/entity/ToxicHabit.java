package com.amachi.app.vitalia.medical.history.entity;
 
import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.common.enums.AlcoholConsumption;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
 
import java.time.LocalDate;
 
/**
 * Registro de consumo de sustancias y hábitos perjudiciales/riesgos de salud (SaaS Elite Tier).
 * Alineado con el estándar de evaluación de riesgos conductuales.
 */
@Entity
@Table(
    name = "MED_TOXIC_HABIT",
    indexes = {
        @Index(name = "IDX_TOX_HAB_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_TOX_HAB_HISTORY", columnList = "FK_ID_MEDICAL_HISTORY")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Evaluación de consumo de sustancias tóxicas (Clinical Tier)")
public class ToxicHabit extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_MEDICAL_HISTORY", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_HAB_TOX_HISTORY"))
    private MedicalHistory medicalHistory;

    @Column(name = "IS_CURRENT", nullable = false)
    @Builder.Default
    private Boolean isCurrent = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "ALCOHOL", nullable = false, length = 50)
    private AlcoholConsumption alcohol;

    @Column(name = "TOBACCO", length = 200)
    private String tobacco;

    @Column(name = "CIGARETTES_PER_DAY")
    private Integer cigarettesPerDay;

    @Column(name = "START_AGE")
    private Integer startAge;

    @Column(name = "QUIT_DATE")
    private LocalDate quitDate;

    @Column(name = "DRUGS", length = 500)
    private String drugs;

    @Column(name = "CAFFEINE", length = 200)
    private String caffeine;

    @Column(name = "SEDENTARY_LIFESTYLE", length = 200)
    private String sedentaryLifestyle;

    @Column(name = "OTHERS", length = 500)
    private String others;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.tobacco != null) this.tobacco = this.tobacco.trim();
        if (this.drugs != null) this.drugs = this.drugs.trim();
        if (this.others != null) this.others = this.others.trim();
    }
}
