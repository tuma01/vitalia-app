package com.amachi.app.vitalia.medical.history.entity;
 
import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.common.enums.ObservationStatus;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
 
import java.time.OffsetDateTime;
 
/**
 * Mediciones clínicas (Signos vitales, laboratorios) alineadas al estándar FHIR Observation (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_OBSERVATION",
    indexes = {
        @Index(name = "IDX_OBS_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_OBS_PATIENT", columnList = "FK_ID_PATIENT"),
        @Index(name = "IDX_OBS_CODE", columnList = "OBS_CODE")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Registro unificado de hallazgo o medición clínica (FHIR Observation)")
public class Observation extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_OBS_PATIENT"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ENCOUNTER", foreignKey = @ForeignKey(name = "FK_MED_OBS_ENC"))
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PRACTITIONER", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_OBS_DOC"))
    private Doctor practitioner;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    private ObservationStatus status;

    @Column(name = "OBS_CODE", nullable = false, length = 100)
    private String code;

    @Column(name = "DISPLAY_NAME", length = 200)
    private String name;

    @Column(name = "VALUE_TEXT", columnDefinition = "TEXT")
    private String value;

    @Column(name = "UNIT", length = 30)
    private String unit;

    @Column(name = "REFERENCE_RANGE", length = 100)
    private String referenceRange;

    @Column(name = "INTERPRETATION", length = 50)
    private String interpretation;

    @Column(name = "EFFECTIVE_DATETIME", nullable = false)
    private OffsetDateTime effectiveDateTime;

    @Column(name = "NOTES", columnDefinition = "TEXT")
    private String notes;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.name != null) this.name = this.name.trim();
        if (this.code != null) this.code = this.code.trim().toUpperCase();
    }
}
