package com.amachi.app.vitalia.user.entity;

import com.amachi.app.vitalia.address.entity.Address;
import com.amachi.app.vitalia.common.utils.EstadoCivilEnum;
import com.amachi.app.vitalia.common.utils.GeneroEnum;
import com.amachi.app.vitalia.common.utils.PersonType;
import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PERSON_TYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "PERSON")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class Person extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERSON_TYPE", nullable = false) //, insertable = false, updatable = false
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PERSON_HOSPITAL",
            joinColumns = @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "HOSPITAL_ID", referencedColumnName = "ID"))
    private Set<Hospital> hospitales = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DEFAULT_HOSPITAL", foreignKey = @ForeignKey(name = "FK_PERSON_DEFAULT_HOSPITAL"))
    private Hospital hospitalPrincipal;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private User user;

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
