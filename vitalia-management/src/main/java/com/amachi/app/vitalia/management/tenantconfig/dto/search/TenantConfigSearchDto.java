package com.amachi.app.vitalia.management.tenantconfig.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class TenantConfigSearchDto implements BaseSearchDto {

    private Long id;
    private String fallbackHeader;
    private String defaultDomain;
    private Long tenantId;

    @Override
    public Long getId() {
        return id;
    }
}
