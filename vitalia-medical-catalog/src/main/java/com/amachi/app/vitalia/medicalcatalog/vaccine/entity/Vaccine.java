package com.amachi.app.vitalia.medicalcatalog.vaccine.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_VACCINE", uniqueConstraints = {
        @UniqueConstraint(name = "UK_VACCINE_CODE", columnNames = {"CODE"})
})
public class Vaccine extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vaccine Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code; // e.g., VAC-001

    @NotBlank(message = "Vaccine Name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 150)
    private String name; // e.g., Hepatitis B, Triple Viral

    @Column(name = "DESCRIPTION", length = 250)
    private String description;

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
