package com.amachi.app.vitalia.medicalcatalog.allergy.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public final class AllergySearchDto implements BaseSearchDto {
    private Long id;
    private String code;
    private String name;
    private String type;
    private Boolean active;
    @Override public Long getId() { return id; }
}
