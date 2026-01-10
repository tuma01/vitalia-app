package com.amachi.app.vitalia.management.email.service;

import com.amachi.app.vitalia.management.email.dto.EmailTemplateName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public interface EmailService {

    /**
     * Envía un email de activación.
     *
     * @param to destinatario
     * @param username nombre del usuario
     * @param templateName template a usar
     * @param activationUrl url de activación
     * @param activationCode código de activación
     * @param subject asunto del email
     */
    void sendActivationEmail(
            String to,
            String username,
            EmailTemplateName templateName,
            String activationUrl,
            String activationCode,
            String subject
    );

    void sendPasswordResetEmail(@NotBlank(message = "Email {err.required}") @Email(message = "El email debe tener un formato válido") String email, String resetToken, @NotEmpty(message = "Tenant code is required") String tenantCode);

    /**
     * Envía un correo para restablecer la contraseña con el token generado.
     * @param recipientEmail Correo del destinatario.
     * @param resetToken Token de restablecimiento.
     */
    void sendPasswordResetEmail(String recipientEmail, String resetToken);
}
