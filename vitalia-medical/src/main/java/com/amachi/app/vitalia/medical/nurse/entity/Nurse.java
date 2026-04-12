package com.amachi.app.vitalia.medical.nurse.entity;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import com.amachi.app.vitalia.medical.professional.entity.ProfessionalInfo;
import com.amachi.app.vitalia.medical.profile.entity.UserProfile;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entidad Nurse (SaaS Elite Tier).
 * Representa al personal de enfermería y staff asistencial técnico.
 * Hereda multi-tenant isolation via Person -> BaseTenantEntity.
 */
@Entity
@Table(name = "MED_NURSE", 
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_NURSE_TENANT_LICENSE", columnNames = {"TENANT_ID", "NURSE_LICENSE"})
    },
    indexes = {
        @Index(name = "IDX_NURSE_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_NURSE_LICENSE", columnList = "NURSE_LICENSE")
    }
)
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("NURSE")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Audited
@Schema(description = "Inpatient and clinical nursing staff profile — SaaS Elite Tier")
@EqualsAndHashCode(callSuper = true, exclude = {"specialities", "professionalInfos", "clinicalSkills"})
public class Nurse extends Person implements SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;

    @Column(name = "NURSE_LICENSE", length = 100)
    private String licenseNumber;

    @Column(name = "NURSE_RANK", length = 100)
    private String rank;

    @Column(name = "NURSE_SHIFT", length = 50)
    private String workShift;

    @Column(name = "IS_ACTIVE")
    @Builder.Default
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DEPT_UNIT", foreignKey = @ForeignKey(name = "FK_MED_NURSE_DEPT_UNIT"))
    private DepartmentUnit departmentUnit;

    @Column(name = "LICENSE_EXPIRY")
    private LocalDate licenseExpiryDate;

    @Column(name = "HIRE_DATE")
    private LocalDate hireDate;

    @Column(name = "CONTRACT_TYPE", length = 50)
    private String contractType;

    @Column(name = "NURSE_EMERGENCY_CONTACT", length = 200)
    private String emergencyContact;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "MED_NURSE_SPECIALITY_MAP",
            joinColumns = @JoinColumn(name = "ID_NURSE", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_SPECIALITY", referencedColumnName = "ID"))
    @Builder.Default
    private Set<MedicalSpecialty> specialities = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_ID_USERPROFILE", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_MED_NURSE_USERPROFILE"))
    private UserProfile profile;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProfessionalInfo> professionalInfos = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USER", foreignKey = @ForeignKey(name = "FK_MED_NURSE_USER"))
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_EMPLOYEE", foreignKey = @ForeignKey(name = "FK_MED_NURSE_HR"))
    private Employee employee;

    @ElementCollection
    @CollectionTable(name = "MED_NURSE_SKILLS", joinColumns = @JoinColumn(name = "ID_NURSE"))
    @Column(name = "SKILL")
    @Builder.Default
    private Set<String> clinicalSkills = new HashSet<>();

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalizeNurse() {
        if (this.licenseNumber != null) this.licenseNumber = this.licenseNumber.trim().toUpperCase();
        if (this.rank != null) this.rank = this.rank.trim().toUpperCase();
    }
}
