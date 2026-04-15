package com.amachi.app.core.auth.entity;

import com.amachi.app.core.auth.enums.InvitationStatus;
import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.enums.RoleContext;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a user invitation sent by a TenantAdmin to onboard a new staff member (Elite Tier).
 * (Manual Implementation to ensure definitive build stability)
 */
@Entity
@Table(
    name = "AUT_USER_INVITATION",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_INVITATION_TOKEN", columnNames = {"TOKEN"})
    },
    indexes = {
        @Index(name = "IDX_INVITATION_STATUS",  columnList = "STATUS"),
        @Index(name = "IDX_INVITATION_TENANT",  columnList = "TENANT_ID"),
        @Index(name = "IDX_INVITATION_USER",    columnList = "FK_ID_USER")
    }
)
public class UserInvitation extends BaseTenantEntity implements Model {

    @NotNull(message = "validation.invitation.roleContext.required")
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_CONTEXT", nullable = false, length = 30)
    private RoleContext roleContext;

    @Column(name = "NATIONAL_ID", length = 30)
    private String nationalId;

    @NotBlank(message = "validation.invitation.email.required")
    @Column(name = "EMAIL", nullable = false, length = 120)
    private String email;

    @NotNull(message = "validation.invitation.tenant.required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "TENANT_ID",
        referencedColumnName = "CODE",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_INVITATION_TENANT")
    )
    private Tenant tenant;

    @NotNull(message = "validation.invitation.role.required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
    @NotBlank(message = "Token {err.required}")
    @Column(name = "TOKEN", nullable = false, unique = true)
    private String token;

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    @Builder.Default
    private InvitationStatus status = InvitationStatus.PENDING;

    @Column(name = "ACCEPTED_AT")
    private LocalDateTime acceptedAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return status == InvitationStatus.PENDING && !isExpired();
    }

    @PrePersist
    @PreUpdate
    private void normalizeInvitation() {
        if (this.email != null) {
            this.email = this.email.toLowerCase().trim();
        }
        if (this.tenant != null && getTenantId() == null) {
            setTenantId(this.tenant.getCode());
        }
    }
}
