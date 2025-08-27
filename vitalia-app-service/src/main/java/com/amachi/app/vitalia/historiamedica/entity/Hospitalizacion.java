package com.amachi.app.vitalia.historiamedica.entity;

import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoHospital;
import com.amachi.app.vitalia.patient.entity.Patient;
import com.amachi.app.vitalia.patient.entity.PatientVisit;
import com.amachi.app.vitalia.room.entity.Room;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "HOSPITALIZACION")
public class Hospitalizacion implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID" , updatable = false, nullable = false)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "FECHA_INGRESO", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaIngreso;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "FECHA_EGRESO")
    private LocalDateTime fechaEgreso;

    @Column(name = "MOTIVO_INGRESO")
    private String motivoIngreso;

    @Column(name = "MOTIVO_EGRESO")
    private String motivoEgreso;

    @Column(name = "DIAGNOSTICO")
    private String diagnostico;

    @Column(name = "TRATAMIENTO")
    private String tratamiento;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ROOM", foreignKey = @ForeignKey(name = "FK_HOSPITALIZACION_ROOM"))
    private Room room;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PATIENT_VISIT", nullable = false, foreignKey = @ForeignKey(name = "FK_HOSPITALIZACION_PATIENTVISIT"))
    private PatientVisit patientVisit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DEPARTAMENTO_HOSPITAL", foreignKey = @ForeignKey(name = "FK_HOSPITALIZACION_DEPARTAMENTOHOSPITAL"))
    private DepartamentoHospital departamentoHospital; // Referencia a tu DTO del departamento

    @Column(name = "PROCEDIMIENTOS_REALIZADOS", columnDefinition = "TEXT")
    private String procedimientosRealizados;

    @ElementCollection
    @CollectionTable(name = "MEDICAMENTOS_AL_ALTA",
            joinColumns = @JoinColumn(name = "ID_HOSPITALIZACION", referencedColumnName = "ID"))
    @Column(name = "MEDICAMENTO")
    private List<String> medicamentosAlAlta;

    @Column(name = "INSTRUCCIONES_PLAN_ALTA", columnDefinition = "TEXT")
    private String instruccionesPlanAlta;

    @Column(name = "CONDICION_AL_ALTA", length = 200)
    private String condicionAlAlta;

    @Column(name = "COMPLICACIONES_DURANTE_ESTANCIA", columnDefinition = "TEXT")
    private String complicacionesDuranteEstancia;

    @Column(name = "RESUMEN_EXAMEN_FISICO_INGRESO", columnDefinition = "TEXT")
    private String resumenExamenFisicoIngreso;

    @Column(name = "TIPO_ADMISION", length = 100)
    private String tipoAdmision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PATIENT", foreignKey = @ForeignKey(name = "FK_HOSPITALIZACION_PATIENT"))
    @JsonBackReference
    private Patient patient;
}
