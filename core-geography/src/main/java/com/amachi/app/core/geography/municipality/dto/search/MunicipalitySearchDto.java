package com.amachi.app.core.geography.municipality.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class MunicipalitySearchDto implements BaseSearchDto {
    private Long id;
    private String name;
    private Long provinceId;
    private Long stateId;
    private Long countryId;

    @Override
    public Long getId() {
        return id;
    }
}
