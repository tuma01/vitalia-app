package com.amachi.app.vitalia.patient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "HabitoToxico", description = "Schema to hold HabitoToxico information")
public class HabitoToxicoDto {

    @Schema(description = "Unique identifier of the toxic habit record", example = "1")
    private Long id;

    @Schema(description = "Indicates if the patient has a history of alcohol consumption", example = "true")
    private String alcohol;

    @Schema(description = "Indicates if the patient has a history of tobacco use", example = "false")
    private String tabaco;

    @Schema(description = "Indicates if the patient has a history of drug use", example = "false")
    private String drogas;

    @Schema(description = "Indicates if the patient consumes infusions", example = "true")
    private String infusiones;

    @Schema(description = "Indicates if the patient consumes caffeine", example = "true")
    private String cafeina;

    @Schema(description = "Indicates if the patient consumes junk food", example = "false")
    private String comidaChatarra;

    @Schema(description = "Indicates if the patient leads a sedentary lifestyle", example = "true")
    private String sedentarismo;

    @Schema(description = "Any other toxic habits not covered by the above fields", example = "Excessive screen time")
    private String otros;
}
