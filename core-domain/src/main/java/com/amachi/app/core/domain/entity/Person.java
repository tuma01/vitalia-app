package com.amachi.app.core.domain.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.common.enums.*;
import com.amachi.app.core.geography.address.entity.Address;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Entidad Person (Elite Tier Standard).
 * Base de Identidad Global (Shared Identity Pattern).
 * Esta entidad NO posee TENANT_ID ya que la identidad es universal en la plataforma.
 */
@Entity
@Table(name = "DMN_PERSON")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"personTenants"})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PERSON_TYPE", discriminatorType = DiscriminatorType.STRING)
@Audited
public class Person extends Auditable<String> implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @Column(name = "NATIONAL_ID", length = 100, unique = true)
    private String nationalId;

    @Column(name = "NATIONAL_HEALTH_ID", length = 100, unique = true)
    private String nationalHealthId;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE", length = 30)
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERSON_TYPE", nullable = false, insertable = false, updatable = false)
    private PersonType personType;

    @NotBlank(message = "{err.required}")
    @Size(min = 2, max = 50)
    @Column(name = "FIRST_NAME", nullable = false) 
    private String firstName;

    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName;

    @NotBlank(message = "{err.required}")
    @Column(name = "LAST_NAME", length = 50, nullable = false)
    private String lastName;

    @Column(name = "SECOND_LAST_NAME", length = 50)
    private String secondLastName;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "MARITAL_STATUS")
    private CivilStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ADDRESS", foreignKey = @ForeignKey(name = "FK_PERSON_ADDRESS"))
    private Address address;

    @Column(name = "PHONE_NUMBER", length = 50)
    private String phoneNumber;

    @Column(name = "MOBILE_NUMBER", length = 50)
    private String mobileNumber;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonTenant> personTenants = new HashSet<>();

    // ==========================================
    // 🧠 Lógica de Normalización (Elite Standard)
    // ==========================================

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.firstName != null) this.firstName = this.firstName.trim();
        if (this.lastName != null) this.lastName = this.lastName.trim();
        if (this.nationalId != null) this.nationalId = this.nationalId.trim().toUpperCase();
        if (this.nationalHealthId != null) this.nationalHealthId = this.nationalHealthId.trim().toUpperCase();
        if (this.email != null) this.email = this.email.trim().toLowerCase();
    }

    @Transient
    public String getFullName() {
        return String.join(" ",
                Optional.ofNullable(firstName).orElse(""),
                Optional.ofNullable(middleName).orElse(""),
                Optional.ofNullable(lastName).orElse(""),
                Optional.ofNullable(secondLastName).orElse(""));
    }
}
