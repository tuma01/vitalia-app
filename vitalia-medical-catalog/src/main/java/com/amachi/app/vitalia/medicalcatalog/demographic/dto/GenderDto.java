package com.amachi.app.vitalia.medicalcatalog.demographic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "Gender", description = "Schema to hold Gender information")
public class GenderDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @JsonProperty("code")
    @NotBlank(message = "Code {err.mandatory}")
    @Schema(description = "Gender Code", example = "M")
    private String code;

    @JsonProperty("name")
    @NotBlank(message = "Name {err.mandatory}")
    @Schema(description = "Gender Name", example = "Masculino")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Status of the record", example = "true")
    private Boolean active;
}
