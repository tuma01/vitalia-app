package com.amachi.app.vitalia.medical.infrastructure.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
 
/**
 * Categorias de unidades hospitalarias (ej: Pediatria, UCI, Cardiologia).
 * Define la especialidad de la unidad en el sistema (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_DEPARTMENT_UNIT_TYPE",
    indexes = {
        @Index(name = "IDX_DUT_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_DUT_NAME", columnList = "NAME")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DepartmentUnitType extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    /**
     * Nombre de la especialidad o tipo de unidad (ej: "Pediatria", "Cuidados Intensivos").
     */
    @NotNull(message = "Nombre del tipo de unidad es obligatorio")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    /**
     * Descripcion detallada de las funciones o servicios prestados en este tipo de unidad.
     */
    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.name != null) this.name = this.name.trim().toUpperCase();
    }
}
