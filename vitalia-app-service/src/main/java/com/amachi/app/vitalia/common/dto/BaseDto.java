package com.amachi.app.vitalia.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class BaseDto {
//    @JsonProperty
//    @Schema(description = "Identificador unico", example = "1")
//    private Long id;
}
