package com.amachi.app.vitalia.medicalcatalog.specialty.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a Medical Specialty catalog entry (Master Data).
 * Can be targeted to Doctors, Nurses, or Both.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_MEDICAL_SPECIALTY", uniqueConstraints = {
        @UniqueConstraint(name = "UK_SPECIALTY_CODE", columnNames = {"CODE"})
})
public class MedicalSpecialty extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Specialty Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code;

    @NotBlank(message = "Specialty Name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 150)
    private String name;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @NotBlank(message = "Target Profession cannot be empty")
    @Column(name = "TARGET_PROFESSION", nullable = false, length = 20)
    @Builder.Default
    private String targetProfession = "BOTH"; // DOCTOR, NURSE, BOTH

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
