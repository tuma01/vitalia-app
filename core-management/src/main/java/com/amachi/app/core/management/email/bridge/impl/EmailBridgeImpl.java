package com.amachi.app.core.management.email.bridge.impl;

import com.amachi.app.core.auth.bridge.EmailBridge;
import com.amachi.app.core.management.email.config.MailingProperties;
import com.amachi.app.core.management.email.dto.EmailTemplateName;
import com.amachi.app.core.management.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Core-management implementation of the {@link EmailBridge} interface.
 *
 * <p>Bridges the core-auth module to the actual email infrastructure
 * (JavaMailSender + Thymeleaf) without creating a direct dependency
 * between modules. Each method delegates to {@link EmailService} with
 * the appropriate template and parameters.
 */
@Component
@RequiredArgsConstructor
public class EmailBridgeImpl implements EmailBridge {

    private final EmailService emailService;
    private final MailingProperties mailingProperties;

    /**
     * {@inheritDoc}
     *
     * <p>Builds the activation URL from {@link MailingProperties} and
     * dispatches via the {@code confirm-email} Thymeleaf template.
     */
    @Override
    public void sendActivationEmail(String to, String username, String activationCode) {
        String activationUrl = mailingProperties.getFrontend().getBaseUrl()
                + mailingProperties.getFrontend().getActivationPath()
                + "?token=" + activationCode;

        emailService.sendActivationEmail(
                to,
                username,
                EmailTemplateName.ACTIVATION_ACCOUNT,
                activationUrl,
                activationCode,
                "Activación de cuenta — Vitalia",
                null);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Dispatches the staff invitation email using the {@code invitation-email}
     * Thymeleaf template. The activationUrl is already fully constructed by
     * {@link com.amachi.app.core.auth.service.impl.InvitationServiceImpl}.
     */
    @Override
    public void sendInvitationEmail(String to, String tenantName, String activationUrl, String nombre, String apellidoPaterno) {
        java.util.Map<String, Object> context = new java.util.HashMap<>();
        context.put("nombre", nombre);
        context.put("apellidoPaterno", apellidoPaterno);
        context.put("tenantName", tenantName);

        emailService.sendActivationEmail(
                to,
                tenantName,                          // used as fallback display name
                EmailTemplateName.INVITATION,
                activationUrl,
                "",
                "Has sido invitado, " + nombre + " — Vitalia",
                context);
    }
}
