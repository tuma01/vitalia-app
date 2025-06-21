package com.amachi.app.vitalia.municipio.dto.search;

import com.amachi.app.vitalia.common.dto.search.BaseSearchDto;
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
