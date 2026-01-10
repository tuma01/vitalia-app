package com.amachi.app.vitalia.medicalcatalog.procedure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "MedicalProcedure", description = "Schema to hold Medical Procedure or Laboratory Test information")
public class MedicalProcedureDto {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @NotBlank(message = "Procedure Code cannot be empty")
    @Size(max = 20, message = "Procedure Code must be at most 20 characters")
    @Schema(description = "Procedure Code (CUPS, CPT, etc.)", example = "90.3.8.01")
    private String code;

    @NotBlank(message = "Procedure Name cannot be empty")
    @Size(max = 500, message = "Procedure Name must be at most 500 characters")
    @Schema(description = "Procedure Name", example = "HEMOGRAMA IV (HEMOGLOBINA, HEMATOCRITO...)")
    private String name;

    @Schema(description = "Type of procedure", example = "LABORATORY")
    private String type;

    @Schema(description = "Status of the procedure", example = "true")
    private Boolean active;
}
