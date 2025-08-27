package com.amachi.app.vitalia.doctor.entity;

import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "DOCTOR_HOSPITAL_ASSIGNMENT")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DoctorHospitalAssignment implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DOCTOR", nullable = false, foreignKey = @ForeignKey(name = "FK_DOCTORHOSPITALASSIGNMENT_DOCTOR"))
    private Doctor doctor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HOSPITAL", nullable = false, foreignKey = @ForeignKey(name = "FK_DOCTORHOSPITALASSIGNMENT_HOSPITAL"))
    private Hospital hospital;

    @PastOrPresent
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate; // Puede ser null si aún trabaja allí

    @PrePersist
    public void prePersist() {
        if (this.startDate == null) {
            this.startDate = LocalDate.now();
        }
    }
}
