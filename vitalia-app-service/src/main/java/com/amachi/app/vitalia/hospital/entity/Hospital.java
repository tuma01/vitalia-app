package com.amachi.app.vitalia.hospital.entity;

import com.amachi.app.vitalia.address.entity.Address;
import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoHospital;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@SuperBuilder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HOSPITAL", uniqueConstraints = {
        @UniqueConstraint(name = "UK_HOSPITAL_CODE", columnNames = {"HOSPITAL_CODE"}),
        @UniqueConstraint(name = "UK_HOSPITAL_TAX_ID", columnNames = {"TAX_IDENTIFICATION_NUMBER"})
})
@EqualsAndHashCode(callSuper = true)
public class Hospital extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Nombre de hospital es requerido")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Size(max = 100, message = "El nombre legal no puede exceder 100 caracteres")
    @Column(name = "LEGAL_NAME", length = 100)
    private String legalName; // Para facturación

    @NotBlank(message = "Código de hospital es requerido")
    @Size(min = 3, max = 20, message = "El código debe tener entre 3 y 20 caracteres")
    @Column(name = "HOSPITAL_CODE", nullable = false, length = 20)
    private String hospitalCode;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @NotBlank(message = "Teléfono principal es requerido")
    @Size(max = 20)
    @Column(name = "TELEPHONE", nullable = false, length = 20)
    private String telephone;

    @Size(max = 20)
    @Column(name = "SECONDARY_TELEPHONE", length = 20)
    private String secondaryTelephone;

    @Email
    @NotBlank(message = "Email es requerido")
    @Size(max = 100)
    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Size(max = 100)
    @Column(name = "WEB_SITE", length = 100)
    private String webSite;

    @Pattern(regexp = "\\d{11}", message = "RUC debe tener 11 dígitos")
    @Column(name = "TAX_IDENTIFICATION_NUMBER", length = 20)
    private String taxIdentificationNumber;

    @Column(name = "LICENSE_NUMBER", length = 50)
    private String licenseNumber; // Número de licencia sanitaria

    @Column(name = "BED_CAPACITY")
    private Integer bedCapacity; // Número de camas

    @Column(name = "FOUNDATION_YEAR")
    private Integer foundationYear;

    @Column(name = "TIME_ZONE", length = 50)
    private String timeZone = "America/La_Paz"; // Zona horaria por defecto

    @Lob
    @Column(name = "LOGO")
    private byte[] logo;

    @Column(name = "IS_DEFAULT", nullable = false)
    private boolean isDefault = false; // Indica si es el hospital predeterminado

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ADDRESS", foreignKey = @ForeignKey(name = "FK_HOSPITAL_ADDRESS"), referencedColumnName = "ID")
    private Address address;

    @JsonManagedReference // ✅ Avoid recursive loop when serializing Patient
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DepartamentoHospital> departamentoHospitals;

    // 🔹 Métodos utilitarios para mantener sincronizada la relación
    public void addDepartamento(DepartamentoHospital departamento) {
        departamentoHospitals.add(departamento);
        departamento.setHospital(this);
    }

    public void removeDepartamento(DepartamentoHospital departamento) {
        departamentoHospitals.remove(departamento);
        departamento.setHospital(null);
    }
}
