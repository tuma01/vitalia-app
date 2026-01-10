package com.amachi.app.vitalia.medicalcatalog.diagnosis.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents an ICD-10 (CIE-10) diagnosis catalog entry.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_ICD10", uniqueConstraints = {
        @UniqueConstraint(name = "UK_ICD10_CODE", columnNames = { "CODE" })
})
public class Icd10 extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "ICD-10 Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code;

    @NotBlank(message = "Description cannot be empty")
    @Column(name = "DESCRIPTION", nullable = false, length = 500)
    private String description;

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
