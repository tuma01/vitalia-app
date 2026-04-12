package com.amachi.app.core.auth.dto.invitation;

import com.amachi.app.core.common.enums.PersonType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * DTO used by the TenantAdmin to send an invitation to a new staff member.
 *
 * <p>The Admin provides only the minimum required data:
 * the recipient's current email, the role to pre-assign,
 * and the tenant context. The invited person will supply
 * their full personal details during self-onboarding.
 */
@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "InvitationRequest",
        description = "Payload for a TenantAdmin to invite a new user to the hospital portal.")
public class InvitationRequest {

    /**
     * Email address to which the invitation link will be sent.
     * Can be the user's personal or professional email — the invited
     * person chooses their definitive login email during onboarding.
     */
    @NotBlank(message = "validation.invitation.email.required")
    @Email(message = "validation.invitation.email.invalid")
    @Size(max = 100, message = "validation.invitation.email.maxLength")
    @Schema(description = "Recipient email for the invitation link.",
            example = "maria.garcia@gmail.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    /**
     * Name of the role to pre-assign once the user completes onboarding.
     * Must match an existing role available in the tenant
     * (ROLE_DOCTOR, ROLE_NURSE, ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_PATIENT).
     */
    @NotBlank(message = "validation.invitation.role.required")
    @Schema(description = "Role name to assign to the user after onboarding.",
            example = "ROLE_DOCTOR",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleName;

    /**
     * Code of the tenant (hospital/clinic) the user is being invited to join.
     * Must correspond to an active tenant registered in the platform.
     */
    @NotBlank(message = "validation.invitation.tenant.required")
    @Schema(description = "Code of the target tenant.",
            example = "hospital-san-borja",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String tenantCode;

    /**
     * The type of person being invited (ADMIN, EMPLOYEE, PATIENT, etc.).
     * This determines which specific Person subclass will be instantiated.
     */
    @NotNull(message = "validation.invitation.personType.required")
    @Schema(description = "Type of person being invited.",
            example = "ADMIN",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private PersonType personType;

    /**
     * First name of the person being invited.
     * Required to satisfy the Person entity constraints in the hybrid model.
     */
    @NotBlank(message = "validation.invitation.firstName.required")
    @Schema(description = "First name of the invitee.",
            example = "Juan",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    /**
     * Last name (father's side) of the person being invited.
     * Required to satisfy the Person entity constraints in the hybrid model.
     */
    @NotBlank(message = "validation.invitation.lastName.required")
    @Schema(description = "Last name of the invitee.",
            example = "Pérez",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;
}
