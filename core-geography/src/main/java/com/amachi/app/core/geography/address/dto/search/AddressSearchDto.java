package com.amachi.app.core.geography.address.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class AddressSearchDto implements BaseSearchDto {

    private Long id;
    private String direccion;
    private String ciudad;
    private String casillaPostal;

    @Override
    public Long getId() {
        return id;
    }
}

