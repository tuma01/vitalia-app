package com.amachi.app.vitalia.doctor.entity;

import com.amachi.app.vitalia.doctorprofessionspeciality.entity.DoctorProfessionSpeciality;
import com.amachi.app.vitalia.historiamedica.entity.HistoriaMedica;
import com.amachi.app.vitalia.user.entity.Person;
import com.amachi.app.vitalia.user.entity.UserProfile;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DOCTOR")
@DiscriminatorValue("DOCTOR")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Doctor extends Person {

    @Column(name = "LICENSE_NUMBER", unique = true, length = 100)
    private String licenseNumber;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "DOCTOR_DOCTORPROFESSIONSPECIALITY",
            joinColumns = @JoinColumn(name = "ID_DOCTOR", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_DOCTOR_PROFESSION_SPECIALITY", referencedColumnName = "ID"))
    private Set<DoctorProfessionSpeciality> doctorProfessionSpecialities;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DoctorHospitalAssignment> doctorHospitalAssignments;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor")
    private Set<HistoriaMedica> historiaMedicas = new HashSet<>();

    @Column(name = "IS_AVAILABLE")
    private Boolean isAvailable = true;

    @Column(name = "SPECIALTIES_SUMMARY")
    private String specialtiesSummary;

    @Column(name = "YEARS_OF_EXPERIENCE")
    private Integer yearsOfExperience;

    @DecimalMin("0.0") @DecimalMax("5.0")
    @Column(name = "RATING", columnDefinition = "DECIMAL(3,1)")
    private Double rating;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_ID_USERPROFILE", foreignKey = @ForeignKey(name = "FK_DOCTOR_USERPROFILE"))
    private UserProfile profile;
}
