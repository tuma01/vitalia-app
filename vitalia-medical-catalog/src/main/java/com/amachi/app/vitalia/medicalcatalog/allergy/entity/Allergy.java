package com.amachi.app.vitalia.medicalcatalog.allergy.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_ALLERGY", uniqueConstraints = {
        @UniqueConstraint(name = "UK_ALLERGY_CODE", columnNames = { "CODE" })
})
public class Allergy extends Auditable<String> implements Model {

    // ID heredado de Auditable

    @NotBlank(message = "Allergy Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code; // e.g., ALL-001

    @NotBlank(message = "Allergy Name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 150)
    private String name; // e.g., Penicilina, Lácteos

    @NotBlank(message = "Allergy Type cannot be empty")
    @Column(name = "TYPE", nullable = false, length = 50)
    private String type; // e.g., DRUG, FOOD, ENVIRONMENTAL

    @Column(name = "DESCRIPTION", length = 250)
    private String description;

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
