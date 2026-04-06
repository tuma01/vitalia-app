package com.amachi.app.core.geography.departamento.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.geography.country.entity.Country;
import jakarta.persistence.*;
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
@Table(
    name = "GEO_DEPARTAMENTO",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_NOMBRE_DEPARTAMENTO_COUNTRY", columnNames = {"NOMBRE", "FK_ID_COUNTRY"})
    }
)
public class Departamento extends Auditable<String> implements Model {

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "POBLACION")
    private Integer poblacion;

    @Column(name = "SUPERFICIE")
    private BigDecimal superficie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_COUNTRY", nullable = false,
                foreignKey = @ForeignKey(name = "FK_DEPARTAMENTO_COUNTRY"))
    private Country country;

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.nombre != null) {
            this.nombre = this.nombre.trim().toUpperCase();
        }
    }
}
