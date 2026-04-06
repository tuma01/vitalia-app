package com.amachi.app.vitalia.medicalcatalog.identity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * Identification Type Data Transfer Object (SaaS Elite Tier).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "IdentificationType", description = "Schema to hold Identification Type information")
public class IdentificationTypeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @JsonProperty("code")
    @NotBlank(message = "Code {err.mandatory}")
    @Size(max = 20)
    @Schema(description = "Identification Type Code", example = "DNI")
    private String code;

    @JsonProperty("name")
    @NotBlank(message = "Name {err.mandatory}")
    @Size(max = 100)
    @Schema(description = "Identification Type Name", example = "Documento Nacional de Identidad")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Status of the identification type record", example = "true")
    private Boolean active;
}
