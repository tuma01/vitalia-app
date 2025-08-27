package com.amachi.app.vitalia.patient.entity;

import com.amachi.app.vitalia.common.utils.VisitTypeEnum;
import com.amachi.app.vitalia.historiamedica.entity.HistoriaMedica;
import com.amachi.app.vitalia.historiamedica.entity.Hospitalizacion;
import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PATIENT_VISIT")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PatientVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PATIENT", nullable = false)
    private Patient patient;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HOSPITAL", nullable = false)
    private Hospital hospital;

    @Column(name = "VISIT_DATE")
    private LocalDate visitDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_ID_HISTORIAMEDICA", foreignKey = @ForeignKey(name = "FK_PATIENTVISIT_HISTORIAMEDICA"))
    private HistoriaMedica historiaMedica;

    // --- NEW: A PatientVisit can have one or more Hospitalizations linked to it ---
    // Mapped by "patientVisit" in the Hospitalizacion entity.
    // Use @JsonManagedReference here if Hospitalizacion has @JsonBackReference.
    @JsonManagedReference
    @OneToMany(mappedBy = "patientVisit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Hospitalizacion> hospitalizaciones = new HashSet<>();

    @Column(name = "VISIT_TYPE", nullable = false) // Make it non-nullable if every visit must have a type
    @Enumerated(EnumType.STRING) // Stores the enum name (e.g., "OUTPATIENT_VISIT") as a string in the DB
    private VisitTypeEnum visitType; // The new field for visit type
}
