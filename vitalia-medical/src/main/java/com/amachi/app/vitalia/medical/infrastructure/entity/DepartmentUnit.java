package com.amachi.app.vitalia.medical.infrastructure.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.vitalia.medicalcatalog.infrastructure.entity.MedicalUnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Physical unit or service within a hospital (e.g., Wing A - Floor 3 - Pediatrics).
 * Master organizational block of the hospital hierarchy (SaaS Elite Tier).
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
     * Clinical specialty or category of the unit (e.g., Cardiology).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_UNIT_TYPE", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_UNIT_TYPE"))
    private MedicalUnitType unitType;

    @Column(name = "NAME", nullable = false, length = 150)
    @Schema(description = "Descriptive name of the unit", example = "Pediatric Wing")
    private String name;

    @Column(name = "CODE", nullable = false, length = 50)
    @Schema(description = "Functional code of the unit", example = "ICU-01")
    private String code;

    /**
     * Operational head or manager (Active employee of the hospital).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_EMPLOYEE", foreignKey = @ForeignKey(name = "FK_MED_UNIT_HEAD"))
    private Employee unitHead;

    /**
     * Rooms physically linked to this unit.
     */
    @Builder.Default
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();

    /**
     * Physical floor where the unit is located (e.g., "Basement", "Floor 4").
     */
    @Column(name = "FLOOR", length = 50)
    private String floor;

    /**
     * Contact phone number or internal extension.
     */
    @Column(name = "CONTACT_PHONE", length = 50)
    private String contactPhone;

    /**
     * Additional details or equipment notes.
     */
    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "IS_ACTIVE")
    @Builder.Default
    private Boolean active = true;

    // --- Organizational Hierarchy ---

    /**
     * Upper-level unit or parent entity (hierarchy).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PARENT_UNIT", foreignKey = @ForeignKey(name = "FK_MED_UNIT_PARENT"))
    private DepartmentUnit parentUnit;

    /**
     * Linked subunits or dependencies.
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

