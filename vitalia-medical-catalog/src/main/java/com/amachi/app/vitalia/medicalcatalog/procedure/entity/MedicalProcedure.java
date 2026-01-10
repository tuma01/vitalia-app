package com.amachi.app.vitalia.medicalcatalog.procedure.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a Medical Procedure or Laboratory Test catalog entry.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_MEDICAL_PROCEDURE", uniqueConstraints = {
        @UniqueConstraint(name = "UK_PROCEDURE_CODE", columnNames = {"CODE"})
})
public class MedicalProcedure extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Procedure Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code;

    @NotBlank(message = "Procedure Name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 500)
    private String name;

    @Column(name = "TYPE", length = 50)
    private String type; // e.g., LABORATORY, SURGERY, IMAGING, CONSULTATION

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
