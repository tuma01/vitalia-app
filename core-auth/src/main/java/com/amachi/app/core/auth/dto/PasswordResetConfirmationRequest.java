package com.amachi.app.core.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Para confirmar el reset (token y nueva contraseña)
// SaaS Elite: Manual implementation to troubleshoot Lombok resolution issues.
import lombok.*;

// Para confirmar el reset (token y nueva contraseña)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetConfirmationRequest {
    @NotBlank(message = "Token is required")
    private String token;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter and one number")
    private String newPassword;
}
