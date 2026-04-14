import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.common.enums.PatientStatus;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import com.amachi.app.vitalia.medical.history.entity.MedicalHistory;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;
import com.amachi.app.vitalia.medical.infrastructure.entity.Bed;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enterprise Patient Entity (SaaS Elite Tier).
 * Rol Clínico con Aislamiento por Tenant + Vínculo a Identidad Global.
 */
@Entity
@Table(
    name = "MED_PATIENT",
    indexes = {
        @Index(name = "IDX_PATIENT_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_PATIENT_PERSON", columnList = "FK_ID_PERSON"),
        @Index(name = "IDX_PATIENT_NHC", columnList = "NHC")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_MED_PAT_IDENTITY_TENANT", columnNames = {"FK_ID_PERSON", "TENANT_ID", "IS_DELETED"}),
        @UniqueConstraint(name = "UK_MED_PAT_TENANT_NHC", columnNames = {"TENANT_ID", "NHC", "IS_DELETED"})
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"medicalHistories", "encounters", "hospitalizations", "allergies", "activeMedications"})
@Audited
@Schema(description = "Registro integral del paciente — SaaS Elite Tier")
public class Patient extends BaseTenantEntity implements Model, SoftDeletable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PERSON", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_PAT_PERSON"))
    private Person person;

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "NHC", nullable = false, length = 50)
    private String nhc;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    @Builder.Default
    private PatientStatus patientStatus = PatientStatus.ACTIVE;

    @Column(name = "PREFERRED_LANGUAGE", length = 20)
    private String preferredLanguage;

    @Column(name = "REGISTRATION_DATE", nullable = false)
    private OffsetDateTime registrationDate;

    // --- Embedded Components ---
    @Embedded
    private PatientDetails patientDetails;

    @Embedded
    private EmergencyContact emergencyContact;

    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;

    // --- Relationships ---
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MedicalHistory> medicalHistories = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Encounter> encounters = new HashSet<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Hospitalization> hospitalizations = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MED_PATIENT_ALLERGY_MAP", joinColumns = @JoinColumn(name = "ID_PATIENT"), inverseJoinColumns = @JoinColumn(name = "ID_ALLERGY"))
    @Builder.Default
    private Set<Allergy> allergies = new HashSet<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Medication> activeMedications = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_CURRENT_HOSPITAL", foreignKey = @ForeignKey(name = "FK_MED_PAT_CUR_HOSP"))
    private Hospitalization currentHospitalization;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_CURRENT_BED", foreignKey = @ForeignKey(name = "FK_MED_PATIENT_BED"))
    private Bed currentBed;

    @Column(name = "IS_ACTIVE")
    @Builder.Default
    private Boolean active = true;

    @Column(name = "ADDITIONAL_REMARKS", columnDefinition = "TEXT")
    private String additionalRemarks;

    // --- Temporary Bridge Methods (Deprecated - Move to Mapper) ---
    @Transient @Deprecated
    public String getFirstName() { return person != null ? person.getFirstName() : null; }
    @Transient @Deprecated
    public String getLastName() { return person != null ? person.getLastName() : null; }
    @Transient @Deprecated
    public String getFullName() { return person != null ? person.getFullName() : null; }
    @Transient @Deprecated
    public String getNationalId() { return person != null ? person.getNationalId() : null; }

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalizePatient() {
        if (this.nhc != null) this.nhc = this.nhc.trim().toUpperCase();
        if (this.registrationDate == null) this.registrationDate = OffsetDateTime.now();
    }

    public MedicalHistory getMedicalHistory() {
        if (this.medicalHistories == null || this.medicalHistories.isEmpty()) return null;
        return this.medicalHistories.stream()
                .filter(mh -> Boolean.TRUE.equals(mh.getIsCurrent()))
                .findFirst()
                .orElse(this.medicalHistories.get(0));
    }
}
