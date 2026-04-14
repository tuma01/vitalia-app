package com.amachi.app.core.geography.province.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class ProvinceSearchDto implements BaseSearchDto {
    private Long id;
    private String name;
    private Long stateId;

    @Override
    public Long getId() {
        return id;
    }
}
