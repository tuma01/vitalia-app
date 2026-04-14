package com.amachi.app.vitalia.medical.doctor.entity;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.vitalia.medical.common.enums.DoctorAvailabilityStatus;
import com.amachi.app.vitalia.medical.history.entity.MedicalHistory;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import com.amachi.app.vitalia.medical.professional.entity.ProfessionalInfo;
import com.amachi.app.vitalia.medical.profile.entity.UserProfile;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.core.common.entity.SoftDeletable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entidad Doctor (SaaS Elite Tier).
 * Rol con Aislamiento por Tenant + Vínculo a Identidad Global.
 */
@Entity
@Table(name = "MED_DOCTOR", 
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_DOCTOR_IDENTITY_TENANT", columnNames = {"FK_ID_PERSON", "TENANT_ID", "IS_DELETED"}),
        @UniqueConstraint(name = "UK_DOCTOR_TENANT_LICENSE", columnNames = {"TENANT_ID", "LICENSE_NUMBER", "IS_DELETED"})
    },
    indexes = {
        @Index(name = "IDX_DOCTOR_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_DOCTOR_PERSON", columnList = "FK_ID_PERSON"),
        @Index(name = "IDX_DOCTOR_LICENSE", columnList = "LICENSE_NUMBER")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"professionalInfos", "assignments", "medicalHistories", "encounters"})
@Audited
@Schema(description = "Perfil integral facultativo — SaaS Elite Tier")
public class Doctor extends BaseTenantEntity implements SoftDeletable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PERSON", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_DOC_PERSON"))
    private Person person;

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "LICENSE_NUMBER", unique = true, length = 100)
    private String licenseNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_ID_USERPROFILE", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_MED_DR_USERPROFILE"))
    private UserProfile profile;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProfessionalInfo> professionalInfos = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "MED_DOCTOR_SPECIALTY_MAP",
            joinColumns = @JoinColumn(name = "ID_DOCTOR", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_SPECIALTY", referencedColumnName = "ID"))
    @Builder.Default
    private Set<MedicalSpecialty> specialties = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<DoctorHospitalAssignment> assignments = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @Builder.Default
    private Set<MedicalHistory> medicalHistories = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Encounter> encounters = new HashSet<>();

    @Column(name = "IS_AVAILABLE")
    @Builder.Default
    private Boolean isAvailable = true;

    @Column(name = "SPECIALTIES_SUMMARY", length = 500)
    private String specialtiesSummary;

    @Column(name = "BIO", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "YEARS_OF_EXPERIENCE")
    private Integer yearsOfExperience;

    @Column(name = "RATING", precision = 3, scale = 1)
    private Double rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USER", foreignKey = @ForeignKey(name = "FK_MED_DOCTOR_USER"))
    private User user;

    @Column(name = "OFFICE_NUMBER", length = 50)
    private String officeNumber;

    @Column(name = "IS_ACTIVE")
    @Builder.Default
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DEPT_UNIT", foreignKey = @ForeignKey(name = "FK_MED_DOCTOR_DEPT_UNIT"))
    private DepartmentUnit departmentUnit;

    @Column(name = "CONSULTATION_PRICE", precision = 12, scale = 2)
    private java.math.BigDecimal consultationPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "AVAILABILITY_STATUS", length = 50)
    private DoctorAvailabilityStatus availabilityStatus;

    @Column(name = "SIGNATURE_DIGITAL_PATH")
    private String signatureDigitalPath;

    @Column(name = "LICENSE_EXPIRY")
    private java.time.LocalDate licenseExpiryDate;

    @Column(name = "HIRE_DATE")
    private java.time.LocalDate hireDate;

    @Column(name = "RAMQ_PROVIDER_NUMBER", length = 50)
    private String providerNumber;

    @Column(name = "TOTAL_CONSULTATIONS")
    @Builder.Default
    private Integer totalConsultations = 0;

    @ElementCollection
    @CollectionTable(name = "MED_DOCTOR_PROCEDURES", joinColumns = @JoinColumn(name = "ID_DOCTOR"))
    @Column(name = "PROCEDURE_NAME")
    @Builder.Default
    private Set<String> clinicalProcedures = new HashSet<>();

    // --- Temporary Bridge Methods (Deprecated) ---
    @Transient @Deprecated
    public String getFirstName() { return person != null ? person.getFirstName() : null; }
    @Transient @Deprecated
    public String getLastName() { return person != null ? person.getLastName() : null; }

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalizeDoctor() {
        if (this.licenseNumber != null) this.licenseNumber = this.licenseNumber.trim().toUpperCase();
        if (this.officeNumber != null) this.officeNumber = this.officeNumber.trim();
        if (this.providerNumber != null) this.providerNumber = this.providerNumber.trim().toUpperCase();
    }
