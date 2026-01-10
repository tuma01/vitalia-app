package com.amachi.app.core.geography.provincia.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "GEO_PROVINCIA", uniqueConstraints = {
                @UniqueConstraint(name = "UK_NOMBRE_PROVINCIA", columnNames = { "NOMBRE" })
})
public class Provincia extends Auditable<String> implements Model {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @NotBlank(message = "El nombre de la Provincia no puede ser nulo o vacío.")
        @Column(name = "NOMBRE", nullable = false, length = 100)
        private String nombre;

        @Column(name = "POBLACION")
        @Schema(description = "Detalla la poblacion de la provincia", example = "8000")
        private Integer poblacion;

        @Column(name = "SUPERFICIE")
        @Schema(description = "Detalla la superficie de la provincia", example = "123.5")
        private BigDecimal superficie;

        @NotNull(message = "Departamento {err.mandatory}")
        @NotNull(message = "Departamento {err.mandatory}")
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "FK_ID_DEPARTAMENTO", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_PROVINCIA_DEPARTAMENTO"))
        private Departamento departamento;
}
