package com.amachi.app.vitalia.medicalcatalog.medication.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class MedicationSearchDto implements BaseSearchDto {
    private Long id;
    private String genericName;
    private String commercialName;
    private String pharmaceuticalForm;
    private Boolean active;

    @Override
    public Long getId() {
        return id;
    }
}
