package com.amachi.app.vitalia.medicalcatalog.vaccine.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public final class VaccineSearchDto implements BaseSearchDto {
    private Long id;
    private String code;
    private String name;
    private Boolean active;
    @Override public Long getId() { return id; }
}
