package com.amachi.app.core.geography.address.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@Entity
@Table(name = "GEO_ADDRESS")
public class Address extends Auditable<String> implements Model {

    @Column(name = "NUMERO", length = 50)
    private String numero;

    @Column(name = "DIRECCION", length = 100)
    private String direccion;

    @Column(name = "BLOQUE", length = 50)
    private String bloque;

    @Column(name = "PISO")
    private Integer piso;

    @Column(name = "NUMERO_DEPARTAMENTO", length = 20)
    private String numeroDepartamento;

    @Column(name = "MEDIDOR", length = 20)
    private String medidor;

    @Column(name = "CASILLA_POSTAL", length = 20)
    private String casillaPostal;

    @Column(name = "CIUDAD", length = 100)
    private String ciudad;

    @Column(name = "LOCATION", length = 100)
    private String location;

    @ManyToOne
    @JoinColumn(name = "FK_ID_COUNTRY", foreignKey = @ForeignKey(name = "FK_ADDRESS_COUNTRY"), referencedColumnName = "ID")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "FK_ID_DEPARTAMENTO", foreignKey = @ForeignKey(name = "FK_ADDRESS_DEPARTAMENTO"), referencedColumnName = "ID")
    private Departamento departamento;
}
