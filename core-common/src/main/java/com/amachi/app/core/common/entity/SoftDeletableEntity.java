package com.amachi.app.core.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad base para recursos que requieren borrado lógico (Soft Delete).
 * Hereda de BaseEntity para incluir ID y TenantID obligatorios.
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class SoftDeletableEntity extends BaseEntity implements SoftDeletable {

    // 🔥 NEW: soft delete solo para entidades que lo necesiten
    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Override
    public void delete() {
        this.isDeleted = true;
    }
}
