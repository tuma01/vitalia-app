package com.amachi.app.core.auth.entity;

import com.amachi.app.core.auth.enums.InvitationStatus;
import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Represents a user invitation sent by a TenantAdmin to onboard a new staff member.
 *
 * <p>When a TenantAdmin assigns a user in the IAM module, an invitation record is persisted
 * here and a secure one-time link is dispatched via email. The invited person uses the link
 * to complete their own profile and credentials (self-onboarding flow).
 *
 * <p>Lifecycle:
 * <pre>
 *   PENDING  →  ACCEPTED  (user completed registration)
 *   PENDING  →  EXPIRED   (token TTL exceeded without acceptance)
 *   PENDING  →  CANCELLED (admin revoked the invitation manually)
 * </pre>
 *
 * @see InvitationStatus
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"tenant", "role", "user"})
@SuperBuilder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(
    name = "AUT_USER_INVITATION",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_INVITATION_TOKEN", columnNames = {"TOKEN"})
    },
    indexes = {
        @Index(name = "IDX_INVITATION_STATUS",  columnList = "STATUS"),
        @Index(name = "IDX_INVITATION_TENANT",  columnList = "FK_ID_TENANT"),
        @Index(name = "IDX_INVITATION_USER",    columnList = "FK_ID_USER")
    }
)
public class UserInvitation extends Auditable<String> implements Model {

    // ──────────────────────────────────────────────
    // Relation to the pre-created User
    // ──────────────────────────────────────────────

    /**
     * The user account associated with this invitation.
     * In the hybrid model, the User (and its specific Person subtype)
     * is created at the time of invitation to ensure data integrity
     * and correct PERSON_TYPE discriminator assignment.
     */
    @NotNull(message = "validation.invitation.user.required")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "FK_ID_USER",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_INVITATION_USER")
    )
    private User user;

    // ──────────────────────────────────────────────
    // Relations
    // ──────────────────────────────────────────────

    /**
     * The tenant (hospital/clinic) to which the user is being invited.
     * Required — every invitation must be scoped to a specific tenant.
     */
    @NotNull(message = "validation.invitation.tenant.required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "FK_ID_TENANT",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_INVITATION_TENANT")
    )
    private Tenant tenant;

    /**
     * The security role pre-assigned by the Admin (e.g. ROLE_DOCTOR, ROLE_NURSE).
     * After self-onboarding this role is granted to the new user account.
     */
    @NotNull(message = "validation.invitation.role.required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "FK_ID_ROLE",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_INVITATION_ROLE")
    )
    private Role role;

    // ──────────────────────────────────────────────
    // Security token
    // ──────────────────────────────────────────────

    /**
     * Cryptographically secure UUID used as the one-time activation token.
     * Embedded in the invitation URL sent via email.
     * Unique across the whole system.
     */
    @NotBlank(message = "validation.invitation.token.required")
    @Column(name = "TOKEN", nullable = false, unique = true, length = 255)
    private String token;

    /**
     * Timestamp at which this invitation token expires.
     * Default: 48 hours from creation.
     * After this point the status should be treated as {@code EXPIRED}.
     */
    @NotNull(message = "validation.invitation.expiresAt.required")
    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    // ──────────────────────────────────────────────
    // Lifecycle status
    // ──────────────────────────────────────────────

    /**
     * Current state of the invitation.
     * Defaults to {@link InvitationStatus#PENDING} at creation.
     */
    @NotNull(message = "validation.invitation.status.required")
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    @Builder.Default
    private InvitationStatus status = InvitationStatus.PENDING;

    /**
     * Timestamp when the invited user completed their self-onboarding.
     * Null until the invitation is {@link InvitationStatus#ACCEPTED}.
     */
    @Column(name = "ACCEPTED_AT")
    private LocalDateTime acceptedAt;

    // ──────────────────────────────────────────────
    // Convenience helpers
    // ──────────────────────────────────────────────

    /**
     * Returns {@code true} if the token has not yet expired and the invitation is still PENDING.
     */
    public boolean isValid() {
        return InvitationStatus.PENDING.equals(this.status)
               && LocalDateTime.now().isBefore(this.expiresAt);
    }
}
