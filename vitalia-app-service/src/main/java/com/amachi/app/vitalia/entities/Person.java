package com.amachi.app.vitalia.entities;

import com.amachi.app.vitalia.address.entity.Address;
import com.amachi.app.vitalia.common.utils.EstadoCivilEnum;
import com.amachi.app.vitalia.common.utils.GeneroEnum;
import com.amachi.app.vitalia.common.utils.PersonType;
import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// NE MARCHE PAS, a ete desactive, on va inserer manuellement le type de personne
//@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "PERSON_TYPE")
@SuperBuilder //genera un constructor con todos los parámetros
@NoArgsConstructor(force = true)
@Table(name = "PERSON")
@EqualsAndHashCode(callSuper=true)
@Data
public class Person extends Auditable<String> implements Model {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERSON_TYPE", nullable = false)
    private PersonType personType;


    @NotBlank(message = "Nombre {err.required}")
    @Size(min = 3, max = 50)
    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @Column(name = "SEGUNDO_NOMBRE", length = 50)
    private String segundoNombre;

    @NotBlank(message = "Apellido paterno {err.required}")
    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 50)
    private String apellidoMaterno;

    @Column(name = "NOMBRE_COMPLETO", length = 100)
    private String nombreCompleto;

    @Column(name = "DIA_NACIMIENTO", length = 2)
    private Integer diaNacimiento;

    @Column(name = "MES_NACIMIENTO", length = 2)
    private Integer mesNacimiento;

    @Column(name = "ANO_NACIMIENTO", length = 4)
    private Integer anoNacimiento;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "FECHA_NACIMIENTO", length = 10)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_CIVIL")
    private EstadoCivilEnum estadoCivil;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENERO")
    private GeneroEnum genero;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ID_ADDRESS", foreignKey = @ForeignKey(name = "FK_PERSON_ADDRESS"), referencedColumnName = "ID")
    private Address address;

    @Column(name = "TELEFONO", length = 50)
    private String telefono;

    @Column(name = "CELULAR", length = 50)
    private String celular;
}
