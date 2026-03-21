package com.amachi.app.core.auth.enums;

/**
 * Lifecycle states of a {@code UserInvitation}.
 *
 * <pre>
 *   PENDING  →  ACCEPTED   Invited user completed self-onboarding.
 *   PENDING  →  EXPIRED    Token TTL elapsed without acceptance.
 *   PENDING  →  CANCELLED  Admin revoked the invitation explicitly.
 * </pre>
 */
public enum InvitationStatus {

    /**
     * Invitation has been sent and is waiting for the user to accept.
     * The token is still valid while the expiry date is in the future.
     */
    PENDING,

    /**
     * The invited user completed the self-onboarding form.
     * A {@code User} account has been created and activated.
     */
    ACCEPTED,

    /**
     * The invitation token TTL elapsed before the user accepted.
     * The Admin must send a new invitation if still needed.
     */
    EXPIRED,

    /**
     * The Admin explicitly revoked the invitation before it was accepted.
     */
    CANCELLED
}
