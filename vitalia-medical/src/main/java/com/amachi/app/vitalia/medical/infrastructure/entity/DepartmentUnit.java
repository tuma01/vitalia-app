package com.amachi.app.vitalia.medical.infrastructure.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Unidad física o servicio dentro de un hospital (ej: Ala A - Piso 3 - Pediatría).
 * Es el bloque organizativo maestro de jerarquía hospitalaria (SaaS Elite Tier).
 */
@Entity
@Table(name = "MED_DEPARTMENT_UNIT", 
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_UNIT_TENANT_CODE", columnNames = {"TENANT_ID", "CODE"}),
        @UniqueConstraint(name = "UK_UNIT_TENANT_NAME", columnNames = {"TENANT_ID", "NAME"})
    },
    indexes = {
        @Index(name = "IDX_UNIT_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_UNIT_CODE", columnList = "CODE")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"rooms", "subUnits"})
public class DepartmentUnit extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    /**
     * Especialidad clínica de la unidad (ej: Cardiología).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_UNIT_TYPE", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_UNIT_TYPE"))
    private DepartmentUnitType unitType;

    @Column(name = "NAME", nullable = false, length = 150)
    @Schema(description = "Nombre descriptivo de la unidad", example = "Pabellón de Lactancia")
    private String name;

    @Column(name = "CODE", nullable = false, length = 50)
    @Schema(description = "Código funcional de la unidad", example = "ICU-01")
    private String code;

    /**
     * Jefe o responsable operativo (Empleado activo del hospital).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_EMPLOYEE", foreignKey = @ForeignKey(name = "FK_MED_UNIT_HEAD"))
    private Employee headOfEmployee;

    /**
     * Habitaciones vinculadas físicamente a esta unidad.
     */
    @Builder.Default
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();

    /**
     * Piso físico donde se ubica la unidad (ej: "Sótano", "Piso 4").
     */
    @Column(name = "FLOOR", length = 50)
    private String floor;

    /**
     * Número telefónico o extensión interna de la unidad.
     */
    @Column(name = "CONTACT_PHONE", length = 50)
    private String contactPhone;

    /**
     * Descripción adicional de la unidad.
     */
    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "IS_ACTIVE")
    @Builder.Default
    private Boolean active = true;

    // --- Jerarquía Organizacional ---

    /**
     * Unidad superior o departamento padre (jerarquía).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PARENT_UNIT", foreignKey = @ForeignKey(name = "FK_MED_UNIT_PARENT"))
    private DepartmentUnit parentUnit;

    /**
     * Sub-unidades o dependencias vinculadas.
     */
    @OneToMany(mappedBy = "parentUnit", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<DepartmentUnit> subUnits = new HashSet<>();

    @Column(name = "MAX_CAPACITY")
    private Integer maxCapacity;

    @Column(name = "IS_CLINICAL")
    @Builder.Default
    private Boolean isClinical = true;

    @Column(name = "COST_CENTER", length = 50)
    private String costCenter;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.name != null) this.name = this.name.trim().toUpperCase();
        if (this.code != null) this.code = this.code.trim().toUpperCase();
        if (this.floor != null) this.floor = this.floor.trim();
    }
}
