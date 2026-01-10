package com.amachi.app.core.common.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * Módulo: vitalia-security
 *
 * Interfaz MOCK para el envío de correos. Esta implementación reside en el módulo vitalia-app-service.
 */
public interface EmailService {
    /**
     * Envía un correo de activación de cuenta con el token generado.
     * @param recipientEmail Correo del destinatario.
     * @param activationToken Token de activación.
     */
    void sendActivationEmail(String recipientEmail, String activationToken);

    void sendPasswordResetEmail(@NotBlank(message = "Email {err.required}") @Email(message = "El email debe tener un formato válido") String email, String resetToken, @NotEmpty(message = "Tenant code is required") String tenantCode);

    /**
     * Envía un correo para restablecer la contraseña con el token generado.
     * @param recipientEmail Correo del destinatario.
     * @param resetToken Token de restablecimiento.
     */
    void sendPasswordResetEmail(String recipientEmail, String resetToken);
}
