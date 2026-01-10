package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "AUT_USER_TENANT_ROLE", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_TENANT_ROLE", columnNames = { "FK_ID_USER", "FK_ID_TENANT", "FK_ID_ROLE" })
})
public class UserTenantRole extends Auditable<String> implements Model {

    // ID heredado de Auditable/BaseEntity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USER", nullable = false, foreignKey = @ForeignKey(name = "FK_USERTENANTROLE_USER"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_TENANT", nullable = false, foreignKey = @ForeignKey(name = "FK_USERTENANTROLE_TENANT"))
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ROLE", nullable = false, foreignKey = @ForeignKey(name = "FK_USERTENANTROLE_ROLE"))
    private Role role;

    /**
     * Metadata opcional: fecha en que se asignó el rol.
     * Mapeado al createdDate de Auditable.
     */
    @Column(name = "ASSIGNED_AT", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    /**
     * Metadata opcional: fecha en que se revocó el rol.
     */
    @Column(name = "REVOKED_AT")
    private LocalDateTime revokedAt;

    /**
     * Estado del rol (activo/inactivo).
     */
    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private boolean active = true;

    @PrePersist
    public void prePersist() {
        if (assignedAt == null) {
            assignedAt = LocalDateTime.now();
        }
    }
}
