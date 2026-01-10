package com.amachi.app.core.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for User entity")
public class UserDto {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @NotBlank(message = "Email {err.mandatory}")
    @Email(message = "Email format is invalid")
    @Schema(description = "User email address", example = "user@example.com")
    private String email;

    @Schema(description = "User password (only for creation or update)", example = "********")
    private String password;

    @Schema(description = "Account enabled status", example = "true")
    private Boolean enabled;

    @Schema(description = "Account locked status", example = "false")
    private Boolean accountLocked;

    @Schema(description = "Last login timestamp")
    private LocalDateTime lastLogin;

    @Schema(description = "ID of the associated Person")
    private Long personId;

    @Schema(description = "Accounts associated with this user")
    private Set<UserAccountDto> userAccounts;

    @Schema(description = "Roles assigned to the user in the current context")
    private Set<String> roles;
}
