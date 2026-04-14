package com.amachi.app.vitalia.medical.infrastructure.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.vitalia.medical.common.enums.CleaningStatus;
import com.amachi.app.vitalia.medical.common.enums.RoomType;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a physical space (Room) intended for patient stay (SaaS Elite Tier).
 * Organized by blocks, floors, and comfort levels.
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
@Schema(description = "Room infrastructure and physical occupancy management")
@EqualsAndHashCode(callSuper = true, exclude = {"beds", "hospitalizations"})
public class Room extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "ROOM_NUMBER", nullable = false, length = 50)
    @Schema(description = "Physical number or identifier of the room", example = "301-A")
    private String roomNumber;

    @Column(name = "IS_PRIVATE", nullable = false)
    @Builder.Default
    @Schema(description = "Indicates if the room is for individual private use")
    private Boolean privateRoom = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROOM_TYPE", nullable = false, length = 50)
    @Builder.Default
    @Schema(description = "Category or comfort level of the room")
    private RoomType roomType = RoomType.STANDARD;

    @Column(name = "BLOCK_FLOOR")
    @Schema(description = "Physical floor or level", example = "3")
    private Integer blockFloor;

    @Column(name = "BLOCK_CODE", length = 50)
    @Schema(description = "Hospital wing or block code", example = "NORTH-WING")
    private String blockCode;

    @Column(name = "DESCRIPTION", length = 500)
    @Schema(description = "Additional details regarding location or equipment")
    private String description;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Bed> beds = new HashSet<>();

    /**
     * Organizational unit to which the room belongs (e.g., Pediatric Wing A).
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
