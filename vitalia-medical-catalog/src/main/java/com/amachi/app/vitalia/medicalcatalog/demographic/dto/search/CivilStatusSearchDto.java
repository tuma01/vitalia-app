package com.amachi.app.vitalia.medicalcatalog.demographic.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CivilStatusSearchDto implements BaseSearchDto {
    private Long id;
    private String code;
    private String name;
    private Boolean active;

    @Override
    public Long getId() {
        return id;
    }
}
