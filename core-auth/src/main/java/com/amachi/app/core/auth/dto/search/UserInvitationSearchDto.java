package com.amachi.app.core.auth.dto.search;

import com.amachi.app.core.auth.enums.InvitationStatus;
import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "UserInvitationSearch", description = "DTO to hold search criteria for UserInvitations")
public class UserInvitationSearchDto implements BaseSearchDto {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Schema(description = "Filter by Invitation Status")
    private InvitationStatus status;

    @Schema(description = "Filter by Role Name")
    private String roleName;

    @Schema(description = "Filter by Email")
    private String email;
    
    @Schema(description = "Code of the tenant to scope results", required = true)
    private String tenantCode;
}
