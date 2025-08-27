package com.amachi.app.vitalia.historiamedica.entity;

import com.amachi.app.vitalia.common.entities.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ENFERMEDAD_ACTUAL")
public class EnfermedadActual implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Size(max = 150)
    @Column(name = "NOMBRE")
    private String nombre;

    @PastOrPresent
    @Column(name = "FECHA_DIAGNOSTICO")
    private LocalDate fechaDiagnostico;

    @Column(name = "SINTOMAS", length = 500)
    private String sintomas;

    @Column(name = "TRATAMIENTOS", length = 500)
    private String tratamientos;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HISTORIA_MEDICA", foreignKey = @ForeignKey(name = "FK_ENFERMEDADACTUAL_HISTORIAMEDICA"))
    private HistoriaMedica historiaMedica;
}
