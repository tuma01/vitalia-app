package com.amachi.app.vitalia.patient.entity;

import com.amachi.app.vitalia.common.entities.Model;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HABITO_TOXICO")
public class HabitoToxico implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ALCOHOL", length = 200)
    private String alcohol;

    @Column(name = "TABACO", length = 200)
    private String tabaco;

    @Column(name = "DROGAS", length = 200)
    private String drogas;

    @Column(name = "INFUSIONES", length = 200)
    private String infusiones;

    @Column(name = "CAFEINA", length = 200)
    private String cafeina;

    @Column(name = "COMIDA_CHATARRA", length = 200)
    private String comidaChatarra;

    @Column(name = "SEDENTARISMO", length = 200)
    private String sedentarismo;

    @Column(name = "OTROS", length = 200)
    private String otros;
}
