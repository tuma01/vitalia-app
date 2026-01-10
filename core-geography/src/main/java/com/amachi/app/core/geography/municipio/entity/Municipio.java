package com.amachi.app.core.geography.municipio.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.geography.provincia.entity.Provincia;
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
@Table(name = "GEO_MUNICIPIO", uniqueConstraints = {
                @UniqueConstraint(name = "UK_NOMBRE_MUNICIPIO", columnNames = { "NOMBRE" })
})
public class Municipio extends Auditable<String> implements Model {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @NotBlank(message = "El nombre del Municipio no puede ser nulo o vacío.")
        @Column(name = "NOMBRE", nullable = false, length = 100)
        private String nombre;

        @Column(name = "DIRECCION", length = 200)
        private String direccion;

        @Column(name = "CODIGO_MUNICIPIO")
        private Integer codigoMunicipio;

        @Column(name = "POBLACION")
        @Schema(description = "Detalla la poblacion del Municipio", example = "8000")
        private Integer poblacion;

        @Column(name = "SUPERFICIE")
        @Schema(description = "Detalla la superficie del Municipio", example = "123.5")
        private BigDecimal superficie;

        @NotNull(message = "Provincia {err.mandatory}")
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "FK_ID_PROVINCIA", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_MUNICIPIO_PROVINCIA"))
        private Provincia provincia;
}
