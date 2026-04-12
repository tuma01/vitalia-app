package com.amachi.app.core.auth.dto.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * DTO submitted by the invited person when completing their self-onboarding.
 *
 * <p>María receives an invitation email containing a one-time link with an embedded token.
 * She opens the link, fills in this form, and submits it. The system then:
 * <ol>
 *     <li>Validates the token is still PENDING and within TTL.</li>
 *     <li>Creates the {@code User} account with the definitive login email and hashed password.</li>
 *     <li>Creates the {@code Person} record with personal details.</li>
 *     <li>Activates the account immediately (Opción A — Auto-activation).</li>
 *     <li>Marks the invitation as {@code ACCEPTED}.</li>
 * </ol>
 */
@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "CompleteRegistrationRequest",
        description = "Payload submitted by the invited person to complete their self-onboarding.")
public class CompleteRegistrationRequest {

    // ──────────────────────────────────────────────
    // Security
    // ──────────────────────────────────────────────

    /**
     * The one-time token extracted from the invitation URL.
     * Used to locate and validate the original {@code UserInvitation}.
     */
    @NotBlank(message = "validation.registration.token.required")
    @Schema(description = "One-time activation token from the invitation URL.",
            example = "a3f9b2c1-4d5e-6789-abcd-ef0123456789",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;

    // ──────────────────────────────────────────────
    // Login credentials (User entity)
    // ──────────────────────────────────────────────

    /**
     * Definitive login email chosen by the user.
     * This will become {@code User.email} — the unique system identifier.
     * May differ from the email the Admin used to send the invitation.
     */
    @NotBlank(message = "validation.registration.loginEmail.required")
    @Email(message = "validation.registration.loginEmail.invalid")
    @Size(max = 100, message = "validation.registration.loginEmail.maxLength")
    @Schema(description = "Definitive login email (User.email). Can be professional or personal.",
            example = "maria@hospital-san-borja.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String loginEmail;

    /**
     * Password chosen by the user. Stored hashed — never plain text.
     * Minimum 8 characters required by security policy.
     */
    @NotBlank(message = "validation.registration.password.required")
    @Size(min = 8, max = 100, message = "validation.registration.password.minLength")
    @Schema(description = "User-chosen password. Minimum 8 characters.",
            example = "Secure@2025!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    // ──────────────────────────────────────────────
    // Personal details (Person entity)
    // ──────────────────────────────────────────────

    /**
     * Legal first name of the person.
     */
    @NotBlank(message = "validation.registration.firstName.required")
    @Size(min = 2, max = 50, message = "validation.registration.firstName.size")
    @Schema(description = "Legal first name.", example = "María", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    /**
     * Optional second given name.
     */
    @Size(max = 50, message = "validation.registration.middleName.maxLength")
    @Schema(description = "Optional second given name.", example = "Alejandra")
    private String middleName;

    /**
     * Legal paternal surname.
     */
    @NotBlank(message = "validation.registration.lastName.required")
    @Size(min = 2, max = 50, message = "validation.registration.lastName.size")
    @Schema(description = "Paternal surname.", example = "García", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    /**
     * Optional maternal surname.
     */
    @Size(max = 50, message = "validation.registration.secondLastName.maxLength")
    @Schema(description = "Maternal surname.", example = "López")
    private String secondLastName;

    /**
     * Mobile phone number. Used for contact purposes within the system — not for authentication.
     */
    @Size(max = 50, message = "validation.registration.phoneNumber.maxLength")
    @Pattern(regexp = "^[+]?[0-9\\s\\-()]{7,20}$", message = "validation.registration.phoneNumber.pattern")
    @Schema(description = "Mobile or office phone number.", example = "+591 70000000")
    private String phoneNumber;

    /**
     * Personal email address stored in the {@code Person} entity as contact detail.
     * Distinct from {@code loginEmail} — this is informational only.
     */
    @Email(message = "validation.registration.personalEmail.invalid")
    @Size(max = 100, message = "validation.registration.personalEmail.maxLength")
    @Schema(description = "Personal contact email (Person.email). Different from the login email.",
            example = "maria.garcia@gmail.com")
    private String personalEmail;

    /**
     * Code of the tenant (hospital/clinic) the user is being associated with.
     * Received during token validation and sent back to ensure consistency.
     */
    @NotBlank(message = "validation.invitation.tenant.required")
    @Schema(description = "Code of the target tenant.", 
            example = "hospital-san-borja", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String tenantCode;
}
