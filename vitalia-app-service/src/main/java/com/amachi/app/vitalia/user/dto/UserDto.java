package com.amachi.app.vitalia.user.dto;

import com.amachi.app.vitalia.role.dto.RoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import lombok.experimental.Accessors;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Schema(name = "User", description = "Schema to hold User information")
public class UserDto {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @JsonProperty
    @NotBlank(message = "Email {err.required}")
    @Email(message = "El email debe tener un formato válido")
    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 64, message = "Password must be between 8 and 64 characters")
//    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,64}$",
//            message = "Password must contain at least one uppercase letter, one number, and be between 8-64 characters")
    @Schema(description = "User password (write-only)", example = "SecureP@ssw0rd!")
    private String password;

    @Schema(description = "Indicates whether the user's account is locked", example = "false")
    private boolean accountLocked;

    @Schema(description = "Indicates whether the user is enabled", example = "true")
    private boolean enabled;

    @Lob
    @Schema(description = "User avatar image in byte array format", example = "Base64 encoded image string")
    private byte[] avatar;

    @NotNull(message = "Roles cannot be null")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "User roles assigned", example = "[\"ADMIN\", \"USER\"]")
    private Set<RoleDto> roles = new HashSet<>();

    @NotNull(message = "Person information is required")
    @Schema(description = "Personal details associated with the user", example = "234L")
    private Long personId;

    public void addRole(RoleDto role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }
}
