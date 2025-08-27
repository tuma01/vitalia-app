package com.amachi.app.vitalia.departamentothospital.entity;

import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.doctor.entity.Doctor;
import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "DEPARTAMENTO_HOSPITAL")
public class DepartamentoHospital extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_HOSPITAL", foreignKey = @ForeignKey(name = "FK_DEPARTAMENTOHOSPITAL_HOSPITAL"))
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_DEPARTAMENTO_TIPO", foreignKey = @ForeignKey(name = "FK_DEPARTAMENTOHOSPITAL_DEPARTAMENTOTIPO"))
    private DepartamentoTipo departamentoTipo;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "FLOOR", length = 20)
    private String floor;

    @Column(name = "CONTACT_PHONE", length = 20)
    private String contactPhone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HEAD_DOCTOR") // opcional
    private Doctor headDoctor;
}
