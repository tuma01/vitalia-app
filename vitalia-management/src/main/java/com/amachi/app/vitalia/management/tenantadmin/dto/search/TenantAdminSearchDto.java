package com.amachi.app.vitalia.management.tenantadmin.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class TenantAdminSearchDto implements BaseSearchDto {

    private Long id;
    private String tenantCode;
    private String email;
    private Boolean enabled;

    @Override
    public Long getId() {
        return id;
    }


}
