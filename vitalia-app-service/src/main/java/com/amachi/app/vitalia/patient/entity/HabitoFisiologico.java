package com.amachi.app.vitalia.patient.entity;

import com.amachi.app.vitalia.common.entities.Model;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HABITO_FISIOLOGICO")
public class HabitoFisiologico implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUTRICION", length = 250)
    private String nutricion;

    @Column(name = "URINACION", length = 250)
    private String urinacion;

    @Column(name = "DEFECACION", length = 250)
    private String defecacion;

    @Column(name = "SUENO", length = 250)
    private String sueno;

    @Column(name = "SEXUALIDAD", length = 250)
    private String sexualidad;

    @Column(name = "ALERGIAS", length = 250)
    private String alergias;

    @Column(name = "ACTIVIDADES_DEPORTIVAS", length = 250)
    private String actividadesDeportivas;

    @Column(name = "OTROS", length = 250)
    private String otros;
}
