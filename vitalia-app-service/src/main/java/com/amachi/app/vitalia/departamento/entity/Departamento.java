package com.amachi.app.vitalia.departamento.entity;

import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.country.entity.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "DEPARTAMENTO", uniqueConstraints = {
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
    @Schema(
            description = "Detalla la poblacion de un departamento", example = "8000"
    )
    private Integer poblacion;

    @Column(name = "SUPERFICIE")
    @Schema(
            description = "Detalla la superficie de un departamento", example = "123.5"
    )
    private BigDecimal superficie;

    @NotNull(message = "Country {err.mandatory}")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ID_COUNTRY", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DEPARTAMENTO_COUNTRY"))
    private Country country;
}
