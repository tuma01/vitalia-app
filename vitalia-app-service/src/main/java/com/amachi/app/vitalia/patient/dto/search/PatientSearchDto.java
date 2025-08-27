package com.amachi.app.vitalia.patient.dto.search;

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
public class PatientSearchDto implements BaseSearchDto {

    @Schema(hidden = true)
    private Long id;
    private String idCard;
    private String nombre;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fullName;

}
