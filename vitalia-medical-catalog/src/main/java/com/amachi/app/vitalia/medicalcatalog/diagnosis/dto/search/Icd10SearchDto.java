package com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class Icd10SearchDto implements BaseSearchDto {
    private Long id;
    private String code;
    private String description;
    private Boolean active;

    @Override
    public Long getId() {
        return id;
    }
}
