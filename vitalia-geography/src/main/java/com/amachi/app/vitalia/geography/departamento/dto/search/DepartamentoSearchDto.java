package com.amachi.app.vitalia.geography.departamento.dto.search;

import com.amachi.app.vitalia.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class DepartamentoSearchDto implements BaseSearchDto {

    private Long id;
    private String nombre;

    @Override
    public Long getId() {
        return id;
    }
}

