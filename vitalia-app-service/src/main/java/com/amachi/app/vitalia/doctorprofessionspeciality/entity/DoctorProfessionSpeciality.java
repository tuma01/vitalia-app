package com.amachi.app.vitalia.doctorprofessionspeciality.entity;

import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "DOCTOR_PROFESSION_SPECIALITY")
public class DoctorProfessionSpeciality extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "DESCRIPTION", length = 250)
    private String description;
}