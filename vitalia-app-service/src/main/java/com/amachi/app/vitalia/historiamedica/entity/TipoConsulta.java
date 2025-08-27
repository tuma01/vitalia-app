package com.amachi.app.vitalia.historiamedica.entity;

import com.amachi.app.vitalia.common.entities.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TIPO_CONSULTA")
public class TipoConsulta implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Size(max = 150)
    @Column(name = "NOMBRE", unique = true, nullable = false)
    private String nombre;

    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Size(max = 250)
    @Column(name = "ESPECIALIDAD")
    private String especialidad;
}
