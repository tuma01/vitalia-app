package com.amachi.app.vitalia.medicalcatalog.allergy.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad Allergy (SaaS Elite Tier).
 * Catálogo médico multi-tenant.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
    name = "CAT_ALLERGY",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_ALLERGY_CODE", columnNames = { "CODE" })
    }
)
public class Allergy extends Auditable<String> implements Model {

    @NotBlank(message = "Code {err.mandatory}")
    @Column(name = "CODE", nullable = false, length = 20)
    private String code;

    @NotBlank(message = "Name {err.mandatory}")
    @Column(name = "NAME", nullable = false, length = 150)
    private String name;

    @NotBlank(message = "Type {err.mandatory}")
    @Column(name = "TYPE", nullable = false, length = 50)
    private String type; // e.g., DRUG, FOOD, ENVIRONMENTAL

    @Column(name = "DESCRIPTION", length = 250)
    private String description;

    @Builder.Default
    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.code != null) this.code = this.code.trim().toUpperCase();
        if (this.name != null) this.name = this.name.trim();
        if (this.type != null) this.type = this.type.trim().toUpperCase();
    }
}
