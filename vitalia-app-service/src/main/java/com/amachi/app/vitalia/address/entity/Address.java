package com.amachi.app.vitalia.address.entity;

import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.country.entity.Country;
import com.amachi.app.vitalia.departamento.entity.Departamento;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "address")
public class Address implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

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
