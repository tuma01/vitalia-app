package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Entity
@Table(name = "AUT_ROLE")
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseTenantEntity implements Model {

    // ID heredado de Auditable/BaseEntity

    @NotBlank(message = "Name {err.required}")
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    // ==========================================
    // 🧠 Lógica de Normalización (Elite Standard)
    // ==========================================

    @PrePersist
    @PreUpdate
    private void normalizeRole() {
        if (this.name != null) {
            this.name = this.name.toUpperCase().trim();
        }
        // Roles Globales residen en 'SYSTEM'
        if (getTenantId() == null) {
            setTenantId("SYSTEM");
        }
    }
}
