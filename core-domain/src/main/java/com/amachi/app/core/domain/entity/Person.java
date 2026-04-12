package com.amachi.app.core.domain.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
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
 * Base para todas las identidades humanas del sistema (Patient, Doctor, Nurse, etc).
 */
@Entity
@Table(name = "DMN_PERSON")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PERSON_TYPE", discriminatorType = DiscriminatorType.STRING)
@Audited
public class Person extends BaseTenantEntity implements Model {

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
    @Column(name = "NOMBRE", nullable = false) 
    private String firstName;

    @Column(name = "SEGUNDO_NOMBRE", length = 50)
    private String middleName;

    @NotBlank(message = "{err.required}")
    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String lastName;

    @Column(name = "APELLIDO_MATERNO", length = 50)
    private String secondLastName;

    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_CIVIL")
    private EstadoCivilEnum maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENERO")
    private GeneroEnum gender;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ADDRESS", foreignKey = @ForeignKey(name = "FK_PERSON_ADDRESS"))
    private Address address;

    @Column(name = "TELEFONO", length = 50)
    private String phoneNumber;

    @Column(name = "CELULAR", length = 50)
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
