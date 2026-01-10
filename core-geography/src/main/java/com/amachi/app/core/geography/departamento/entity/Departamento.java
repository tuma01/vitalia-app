package com.amachi.app.core.geography.departamento.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.geography.country.entity.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "GEO_DEPARTAMENTO", uniqueConstraints = {
        @UniqueConstraint(name = "UK_NOMBRE_DEPARTAMENTO", columnNames = {"NOMBRE"})
})
public class Departamento extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Name of Departamento can not be a null or empty")
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "POBLACION")
    private Integer poblacion;

    @Column(name = "SUPERFICIE")
    private BigDecimal superficie;

    @NotNull(message = "Country {err.mandatory}")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ID_COUNTRY", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DEPARTAMENTO_COUNTRY"))
    private Country country;
}
