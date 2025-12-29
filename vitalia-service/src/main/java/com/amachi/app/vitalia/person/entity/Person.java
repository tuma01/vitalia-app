package com.amachi.app.vitalia.person.entity;

import com.amachi.app.vitalia.common.entity.Auditable;
import com.amachi.app.vitalia.common.entity.Model;
import com.amachi.app.vitalia.common.enums.EstadoCivilEnum;
import com.amachi.app.vitalia.common.enums.GeneroEnum;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.geography.address.entity.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "PERSON")
// 🛡️ SECURITY: Tenant Isolation Filter
// Enforces that only Persons linked to the current Tenant (via PERSON_TENANT)
// are visible.
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = Long.class))
@Filter(name = "tenantFilter", condition = "exists (select 1 from person_tenant pt where pt.fk_id_person = id and pt.fk_id_tenant = :tenantId)")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PERSON_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Person extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "NATIONAL_ID", nullable = true, length = 100, unique = true)
    private String nationalId;

    @Column(name = "NATIONAL_HEALTH_ID", nullable = true, length = 100, unique = true)
    private String nationalHealthId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERSON_TYPE", nullable = false, insertable = false, updatable = false)
    private PersonType personType;

    @NotBlank(message = "Nombre {err.required}")
    @Size(min = 3, max = 50)
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "SEGUNDO_NOMBRE", length = 50)
    private String segundoNombre;

    @NotBlank(message = "Apellido paterno {err.required}")
    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 50)
    private String apellidoMaterno;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_CIVIL")
    private EstadoCivilEnum estadoCivil;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENERO")
    private GeneroEnum genero;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ID_ADDRESS", foreignKey = @ForeignKey(name = "FK_PERSON_ADDRESS"))
    private Address address;

    @Column(name = "TELEFONO", length = 50)
    private String telefono;

    @Column(name = "CELULAR", length = 50)
    private String celular;

    // 🔹 Relación con Tenant (hospital)
    @Builder.Default
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonTenant> personTenants = new HashSet<>();

    // Opción para construir nombre completo
    @Transient
    public String getNombreCompleto() {
        return String.join(" ",
                Optional.ofNullable(nombre).orElse(""),
                Optional.ofNullable(segundoNombre).orElse(""),
                Optional.ofNullable(apellidoPaterno).orElse(""),
                Optional.ofNullable(apellidoMaterno).orElse(""));
    }
}
