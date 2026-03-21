package com.amachi.app.core.auth.bridge;

/**
 * Bridge interface that decouples the {@code core-auth} module from the email
 * infrastructure implementation located in {@code core-management}.
 *
 * <p>All email-sending operations must be routed through this bridge to preserve
 * the hexagonal architecture and avoid direct dependencies between modules.
 */
public interface EmailBridge {

    /**
     * Sends an account activation email to a self-registered user.
     * The link contains the activation code/token.
     *
     * @param to             recipient email address
     * @param username       display name (or email) shown in the email body
     * @param activationCode one-time activation token
     */
    void sendActivationEmail(
            String to,
            String username,
            String activationCode
    );

    /**
     * Sends a staff invitation email dispatched by a TenantAdmin.
     * The email contains a secure one-time link pointing to the self-onboarding page.
     *
     * @param to             recipient email address (personal or professional)
     * @param tenantName     name of the hospital/clinic for display in the email body
     * @param activationUrl  full URL of the onboarding page including the token query param
     * @param nombre         first name of the invitee
     * @param apellidoPaterno paternal last name of the invitee
     */
    void sendInvitationEmail(
            String to,
            String tenantName,
            String activationUrl,
            String nombre,
            String apellidoPaterno
    );
}
