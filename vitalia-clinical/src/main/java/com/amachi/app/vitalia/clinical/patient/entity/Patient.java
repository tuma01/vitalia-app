package com.amachi.app.vitalia.clinical.patient.entity;

import com.amachi.app.core.common.enums.PatientStatus;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.domain.entity.Person;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Entity
//@Table(name = "PATIENT")
//@DiscriminatorValue("PATIENT")
//@Getter @Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@SuperBuilder
//@EqualsAndHashCode(callSuper = true)
public class Patient extends Person {

//    @Column(name = "ID_CARD", unique = true, length = 100)
//    private String idCard;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "PATIENT_STATUS", nullable = false)
//    private PatientStatus patientStatus;
//
//    @Column(name = "OCCUPATION", length = 100)
//    private String occupation;
//
//    @Column(name = "NATIONALITY", length = 100)
//    private String nationality;
//
//    @Column(name = "DEGREE_OF_INSTRUCTION", length = 100)
//    private String degreeOfInstruction;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "FK_ID_COUNTRY")
//    private Country countryOfBirth;
//
//    @JoinColumn(name = "FK_ID_ROOM")
//    private Long roomId;
//
//    @Lob
//    @Column(name = "PHOTO")
//    private byte[] photo;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "FK_ID_NURSE")
//    private Nurse nurse;
//
//    @Column(name = "ADDITIONAL_REMARKS", columnDefinition = "TEXT")
//    private String additionalRemarks;
//
//    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private Set<PatientVisit> visits = new HashSet<>();
//
//    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Set<Hospitalizacion> hospitalizaciones = new HashSet<>();
//
//    // ≡ƒö╣ Agrupamos biom├⌐tricos y otros detalles
//    @Embedded
//    private PatientDetails patientDetails;
//
//    // ≡ƒö╣ Multi-tenant: relaci├│n a trav├⌐s de PersonTenant
//    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<PersonTenant> patientTenants = new HashSet<>();
//
//    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MedicalHistory> medicalHistories = new ArrayList<>();
//
//    @Embedded
//    private EmergencyContact emergencyContact;
//
//    @Column(name = "FECHA_REGISTRO_PACIENTE", nullable = false, updatable = false)
//    private LocalDateTime fechaRegistroPaciente;
//
//    @Column(name = "FECHA_ACTUALIZACION_PACIENTE")
//    private LocalDateTime fechaActualizacionPaciente;
//
//    @PrePersist
//    public void prePersist() {
//        this.fechaRegistroPaciente = LocalDateTime.now();
//        this.fechaActualizacionPaciente = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        this.fechaActualizacionPaciente = LocalDateTime.now();
//    }
}
