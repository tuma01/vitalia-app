package com.amachi.app.vitalia.nurseprofessionspeciality.dto.search;

import com.amachi.app.vitalia.common.dto.search.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class NurseProfessionSpecialitySearchDto implements BaseSearchDto {
    @Schema(hidden = true)
    private Long id;
    private String name;
}
