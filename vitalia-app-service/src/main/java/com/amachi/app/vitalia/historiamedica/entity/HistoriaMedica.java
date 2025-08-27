package com.amachi.app.vitalia.historiamedica.entity;

import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.doctor.entity.Doctor;
import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.amachi.app.vitalia.patient.entity.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HISTORIA_MEDICA")
public class HistoriaMedica implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false, foreignKey = @ForeignKey(name = "FK_HISTORIAMEDICA_PATIENT"))
    private Patient patient;

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DOCTOR", nullable = false, foreignKey = @ForeignKey(name = "FK_HISTORIAMEDICA_DOCTOR"))
    private Doctor doctor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HOSPITAL", nullable = false, foreignKey = @ForeignKey(name = "FK_HISTORIAMEDICA_HOSPITAL"))
    private Hospital hospital;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ADMISSION_DATE", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime admissionDate;

    @Column(name = "OBSERVATIONS", length = 1000)
    private String observations;

    @OneToMany(mappedBy = "historiaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultaMedica> consultasMedicas = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "historiaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnfermedadActual> enfermedadesActuales = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "historiaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroEnfermedad> registroEnfermedades = new ArrayList<>();
}
