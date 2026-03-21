package com.amachi.app.core.auth.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class RoleSearchDto implements BaseSearchDto {
    private Long id;
    private String name;

    @Override
    public Long getId() {
        return id;
    }
}
