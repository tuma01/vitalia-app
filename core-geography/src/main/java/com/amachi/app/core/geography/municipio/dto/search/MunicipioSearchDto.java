package com.amachi.app.core.geography.municipio.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class MunicipioSearchDto implements BaseSearchDto {
    @Schema(hidden = true)
    private Long id;
    private String nombre;
    private String codigoMunicipio;
}
