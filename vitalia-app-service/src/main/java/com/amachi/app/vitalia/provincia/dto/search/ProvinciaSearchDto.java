package com.amachi.app.vitalia.provincia.dto.search;

import com.amachi.app.vitalia.common.dto.search.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class ProvinciaSearchDto implements BaseSearchDto {
    @Schema(hidden = true)
    private Long id;
    private String nombre;

    @Override
    public Long getId() {
        return id;
    }
}
