package com.amachi.app.core.domain.tenant.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class TenantSearchDto implements BaseSearchDto {

    private Long id;
    private String code;
    private String name;
    private String type;
    private Boolean isActive;

    @Override
    public Long getId() {
        return id;
    }
}

