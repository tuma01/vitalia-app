package com.amachi.app.vitalia.historiamedica.entity;

import com.amachi.app.vitalia.common.entities.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REGISTRO_ENFERMEDAD")
public class RegistroEnfermedad implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Size(max = 150)
    @Column(name = "NOMBRE_ENFERMEDAD")
    private String nombreEnfermedad;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "SINTOMAS", length = 500)
    private String sintomas;

    @Column(name = "TRATAMIENTOS", length = 500)
    private String tratamientos;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HISTORIA_MEDICA", foreignKey = @ForeignKey(name = "FK_REGISTROENFERMEDAD_HISTORIAMEDICA"))
    private HistoriaMedica historiaMedica;
}
