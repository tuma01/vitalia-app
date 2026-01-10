package com.amachi.app.vitalia.management.superadmin.dto.search;

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
public class SuperAdminSearchDto implements BaseSearchDto {

    private Long id;
    private Boolean globalAccess;
    private Long userId;

    @Override
    public Long getId() {
        return id;
    }
}
