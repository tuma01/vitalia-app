package com.amachi.app.core.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Request body for assigning or unassigning multiple roles to a user within a
 * tenant.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AssignRolesRequest", description = "Assign or unassign role names to a user in a tenant")
public class AssignRolesRequest {
    @NotNull
    @Schema(description = "ID del usuario", example = "1")
    private Long userId;

    @NotNull
    @Schema(description = "ID del tenant", example = "1")
    private Long tenantId;

    @NotEmpty
    @Schema(description = "Lista de nombres de roles a asignar o desasignar", example = "[\"ADMIN\", \"USER\"]")
    private List<String> roleNames;
}
