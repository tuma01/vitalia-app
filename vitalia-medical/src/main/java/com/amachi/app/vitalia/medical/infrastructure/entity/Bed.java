package com.amachi.app.vitalia.medical.infrastructure.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.common.enums.BedStatus;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

/**
 * Enterprise Bed Entity (SaaS Elite Tier).
 * Representa la unidad mínima de ocupación hospitalaria.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "MED_BED",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_BED_TENANT_ROOM_CODE",
                        columnNames = {"TENANT_ID", "FK_ID_ROOM", "BED_CODE"}
                )
        },
        indexes = {
            @Index(name = "IDX_BED_TENANT", columnList = "TENANT_ID"),
            @Index(name = "IDX_BED_ROOM", columnList = "FK_ID_ROOM")
        }
)
@Audited
@Schema(description = "Gestión de unidades de cama hospitalaria")
@EqualsAndHashCode(callSuper = true)
public class Bed extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "BED_NUMBER", nullable = false, length = 50)
    @Schema(description = "Número para rotulación física", example = "CAMA 01-A")
    private String bedNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_ROOM", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_BED_ROOM"))
    private Room room;

    @Column(name = "BED_CODE", nullable = false, length = 50)
    @Schema(description = "Código secuencial o identificador interno", example = "C01A")
    private String bedCode;

    @Column(name = "IS_OCCUPIED", nullable = false)
    @Builder.Default
    private Boolean isOccupied = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "BED_STATUS", length = 30)
    @Builder.Default
    private BedStatus status = BedStatus.AVAILABLE;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HOSPITALIZATION", foreignKey = @ForeignKey(name = "FK_MED_BED_HOSPITALIZATION"))
    private Hospitalization hospitalization;

    @Column(name = "MAINTENANCE_DUE")
    private LocalDate maintenanceDue;

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
        if (this.bedNumber != null) this.bedNumber = this.bedNumber.trim().toUpperCase();
        if (this.bedCode != null) this.bedCode = this.bedCode.trim().toUpperCase();
    }
}
