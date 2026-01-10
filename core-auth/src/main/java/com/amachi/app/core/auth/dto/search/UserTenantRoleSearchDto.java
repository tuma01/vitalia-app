package com.amachi.app.core.auth.dto.search;

import com.amachi.app.core.auth.dto.UserDto;
import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class UserTenantRoleSearchDto implements BaseSearchDto {
    private Long id;
    private UserDto user;
    private Long tenantId;
    private LocalDateTime assignedAt;
    private LocalDateTime revokedAt;
    private Boolean active = true;

    @Override
    public Long getId() {
        return id;
    }
}

