package com.amachi.app.core.domain.hospital.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class HospitalSearchDto implements BaseSearchDto {
    private Long id;
    private String legalName;
    private String taxId;
    private String medicalLicense;

    @Override
    public Long getId() {
        return id;
    }
}
