package com.amachi.app.vitalia.doctor.dto.search;

import com.amachi.app.vitalia.common.dto.search.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class DoctorSearchDto implements BaseSearchDto {

    private Long id;
    private String numeroId;
    private String nombre;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
}
