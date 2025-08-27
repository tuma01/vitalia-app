package com.amachi.app.vitalia.patient.entity;

import com.amachi.app.vitalia.common.utils.BloodGroup;
import com.amachi.app.vitalia.common.utils.PatientStatus;
import com.amachi.app.vitalia.country.entity.Country;
import com.amachi.app.vitalia.historiamedica.entity.Hospitalizacion;
import com.amachi.app.vitalia.nurse.entity.Nurse;
import com.amachi.app.vitalia.room.entity.Room;
import com.amachi.app.vitalia.user.entity.Person;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PATIENT")
@DiscriminatorValue("PATIENT")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Patient extends Person {

    @Column(name = "ID_CARD", unique = true, length = 100)
    private String idCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "PATIENT_STATUS", nullable = false)
    private PatientStatus patientStatus;

    @Column(name = "OCCUPATION", length = 100)
    private String occupation;

    @Column(name = "NATIONALITY", length = 100)
    private String nationality;

    @Column(name = "DEGREE_OF_INSTRUCTION", length = 100)
    private String degreeOfInstruction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_COUNTRY_OF_BIRTH")
    private Country countryOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ROOM")
    private Room room;

    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_INSURANCE")
    private Insurance insurance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_NURSE")
    private Nurse nurse;

    @Column(name = "ADDITIONAL_REMARKS", columnDefinition = "TEXT")
    private String additionalRemarks;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_HABITO_TOXICO")
    private HabitoToxico habitoToxico;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_HABITO_FISIOLOGICO")
    private HabitoFisiologico habitoFisiologico;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_HISTORIA_FAMILIAR")
    private HistoriaFamiliar historiaFamiliar;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PatientVisit> visits = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "BLOOD_GROUP", length = 6)
    private BloodGroup bloodGroup;

    @Column(name = "WEIGHT", precision = 5, scale = 2)
    private BigDecimal weight;

    @Column(name = "HEIGHT", precision = 5, scale = 2)
    private BigDecimal height;

    @Embedded
    private EmergencyContact emergencyContact;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Hospitalizacion> hospitalizaciones = new HashSet<>();

    @Column(name = "FECHA_REGISTRO_PACIENTE", nullable = false, updatable = false)
    private LocalDateTime fechaRegistroPaciente;

    @Column(name = "FECHA_ACTUALIZACION_PACIENTE")
    private LocalDateTime fechaActualizacionPaciente;

    @Column(name = "TIENE_DISCAPACIDAD")
    private Boolean tieneDiscapacidad;

    @Column(name = "DETALLES_DISCAPACIDAD", length = 250)
    private String detallesDiscapacidad;

    @Column(name = "EMBARAZADA")
    private Boolean embarazada;

    @Column(name = "SEMANAS_EMBARAZO")
    private Integer semanasEmbarazo;

    @Column(name = "NUMERO_HIJOS")
    private Integer numeroHijos;

    @Column(name = "GRUPO_ETNICO", length = 100)
    private String grupoEtnico;

    @PrePersist
    public void prePersist() {
        this.fechaRegistroPaciente = LocalDateTime.now();
        this.fechaActualizacionPaciente = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacionPaciente = LocalDateTime.now();
    }
}
