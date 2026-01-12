package com.amachi.app.vitalia.medicalcatalog.medication.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a Medication catalog entry (Vademecum).
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_MEDICATION", uniqueConstraints = {
        @UniqueConstraint(name = "UK_MEDICATION_CODE", columnNames = { "CODE" })
})
public class Medication extends Auditable<String> implements Model {

    @NotBlank(message = "Medication Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code;

    @NotBlank(message = "Generic Name cannot be empty")
    @Column(name = "GENERIC_NAME", nullable = false, length = 250)
    private String genericName;

    @Column(name = "COMMERCIAL_NAME", length = 250)
    private String commercialName;

    @Column(name = "CONCENTRATION", length = 100)
    private String concentration; // e.g., 500mg, 10mg/5ml

    @Column(name = "PHARMACEUTICAL_FORM", length = 100)
    private String pharmaceuticalForm; // e.g., TABLET, SYRUP, INHALER

    @Column(name = "PRESENTATION", length = 250)
    private String presentation; // e.g., BOX X 30 TABLETS

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
