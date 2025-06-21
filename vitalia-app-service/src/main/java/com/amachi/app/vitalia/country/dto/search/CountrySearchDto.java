package com.amachi.app.vitalia.country.dto.search;

import com.amachi.app.vitalia.common.dto.search.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class CountrySearchDto implements BaseSearchDto {
    private Long id;
    private String iso;
    private String name;

    @Override
    public Long getId() {
        return id;
    }
}

