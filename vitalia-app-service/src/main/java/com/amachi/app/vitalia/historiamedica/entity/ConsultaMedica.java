package com.amachi.app.vitalia.historiamedica.entity;

import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.common.utils.EstadoConsultaEnum;
import com.amachi.app.vitalia.doctor.entity.Doctor;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CONSULTA_MEDICA")
public class ConsultaMedica implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_CONSULTA", nullable = false)
    private EstadoConsultaEnum estadoConsulta;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_TIPO_CONSULTA", foreignKey = @ForeignKey(name = "FK_CONSULTAMEDICA_TIPOCONSULTA"))
    private TipoConsulta tipoConsulta;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "FECHA_CONSULTA", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaConsulta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_DOCTOR", foreignKey = @ForeignKey(name = "FK_CONSULTAMEDICA_DOCTOR"))
    private Doctor doctor;

    @Column(name = "MOTIVO_CONSULTA", length = 500)
    private String motivoConsulta;

    @Column(name = "SINTOMAS", length = 500)
    private String sintomas;

    @Column(name = "DIAGNOSTICO", length = 500)
    private String diagnostico;

    @Column(name = "TRATAMIENTO", length = 500)
    private String tratamiento;

    @Column(name = "RECOMENDACIONES", length = 500)
    private String recomendaciones;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "FECHA_PROXIMA_CITA")
    private LocalDateTime fechaProximaCita;

    @Column(name = "OBSERVACIONES", length = 1000)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_HISTORIA_MEDICA", foreignKey = @ForeignKey(name = "FK_CONSULTAMEDICA_HISTORIAMEDICA"))
    private HistoriaMedica historiaMedica;
}
