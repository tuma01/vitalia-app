package com.amachi.app.vitalia.medical.history.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.common.enums.EpisodeOfCareStatus;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Agrupador macro de eventos clínicos (Ginecología - Embarazo, Oncología - Tratamiento).
 * Gestiona el ciclo de vida de un cuidado continuo según el estándar FHIR EpisodeOfCare (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_EPISODE_OF_CARE",
    indexes = {
        @Index(name = "IDX_EPI_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_EPI_STATUS", columnList = "STATUS"),
        @Index(name = "IDX_EPI_PATIENT", columnList = "FK_ID_PATIENT")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"relatingConditions", "encounters"})
@Audited
@Schema(description = "Gestión de episodio de cuidados prolongados o crónicos (FHIR EpisodeOfCare)")
public class EpisodeOfCare extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_EPI_PATIENT"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_MANAGING_PRACTITIONER", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_EPI_DOC"))
    private Doctor managingPractitioner;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    private EpisodeOfCareStatus status;

    @Column(name = "TYPE", length = 100)
    private String typeDescription;

    @Column(name = "PERIOD_START", nullable = false)
    private LocalDateTime periodStart;

    @Column(name = "PERIOD_END")
    private LocalDateTime periodEnd;

    @OneToMany(mappedBy = "episodeOfCare", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Condition> relatingConditions = new ArrayList<>();

    @OneToMany(mappedBy = "episodeOfCare", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Encounter> encounters = new ArrayList<>();

    @Column(name = "GOALS", columnDefinition = "TEXT")
    private String goals;

    @Column(name = "NOTES", columnDefinition = "TEXT")
    private String notes;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.typeDescription != null) this.typeDescription = this.typeDescription.trim().toUpperCase();
        if (this.goals != null) this.goals = this.goals.trim();
    }
}
