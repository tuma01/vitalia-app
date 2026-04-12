package com.amachi.app.vitalia.medical.infrastructure.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.common.enums.CleaningStatus;
import com.amachi.app.vitalia.medical.common.enums.TypeRoomEnum;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa un espacio físico (Habitación) destinado a la estancia del paciente (SaaS Elite Tier).
 * Organizado por bloques, pisos y tipos de confort.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "MED_ROOM",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_ROOM_TENANT_UNIT_NUMBER",
                columnNames = {"TENANT_ID", "FK_ID_DEPT_UNIT", "ROOM_NUMBER"}
        ),
        indexes = {
            @Index(name = "IDX_ROOM_TENANT", columnList = "TENANT_ID"),
            @Index(name = "IDX_ROOM_UNIT", columnList = "FK_ID_DEPT_UNIT")
        }
)
@Audited
@Schema(description = "Gestión de infraestructura de habitaciones")
@EqualsAndHashCode(callSuper = true, exclude = {"beds", "hospitalizations"})
public class Room extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "ROOM_NUMBER", nullable = false, length = 50)
    @Schema(description = "Número o identificador físico de la habitación", example = "301-A")
    private String roomNumber;

    @Column(name = "IS_PRIVATE", nullable = false)
    @Builder.Default
    @Schema(description = "Indica si la habitación es de uso privado individual")
    private Boolean privateRoom = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROOM_TYPE", nullable = false, length = 50)
    @Builder.Default
    @Schema(description = "Categoría o nivel de confort de la habitación")
    private TypeRoomEnum typeRoom = TypeRoomEnum.ESTANDAR;

    @Column(name = "BLOCK_FLOOR")
    @Schema(description = "Piso o nivel donde se encuentra", example = "3")
    private Integer blockFloor;

    @Column(name = "BLOCK_CODE", length = 50)
    @Schema(description = "Código del ala o bloque hospitalario", example = "ALA-NORTE")
    private String blockCode;

    @Column(name = "DESCRIPTION", length = 500)
    @Schema(description = "Detalle adicional sobre la ubicación o equipamiento")
    private String description;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Bed> beds = new HashSet<>();

    /**
     * Unidad organizacional a la que pertenece la habitación (ej: Ala A de Pediatría).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_DEPT_UNIT", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_ROOM_UNIT"))
    private DepartmentUnit unit;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Hospitalization> hospitalizations = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "CLEANING_STATUS", length = 30)
    @Builder.Default
    private CleaningStatus cleaningStatus = CleaningStatus.CLEAN;

    @Column(name = "IS_ACTIVE")
    @Builder.Default
    private Boolean active = true;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.roomNumber != null) this.roomNumber = this.roomNumber.trim().toUpperCase();
        if (this.blockCode != null) this.blockCode = this.blockCode.trim().toUpperCase();
    }

    // Helpers
    public void addBed(Bed bed) {
        beds.add(bed);
        bed.setRoom(this);
    }
}
