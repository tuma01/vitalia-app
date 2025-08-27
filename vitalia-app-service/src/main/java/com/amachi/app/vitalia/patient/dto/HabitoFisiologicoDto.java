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
@Schema(name = "HabitoFisiologico", description = "Schema to hold HabitoFisiologico information")
public class HabitoFisiologicoDto {

    @Schema(description = "Unique identifier of the physiological habit", example = "1")
    private Long id;

    @Schema(description = "Indicates the patient's nutritional habits or dietary information", example = "Balanced diet, Vegetarian, High-sugar diet")
    private String nutricion;

    @Schema(description = "Indicates the patient's hydration habits", example = "2 liters per day")
    private String urinacion;

    @Schema(description = "Indicates the patient's defecation habits, frequency and characteristics", example = "Regular, once daily; Irregular; Constipated; Diarrhea")
    private String defecacion;

    @Schema(description = "Indicates the patient's sleep patterns and habits", example = "8 hours per night; Insomnia; Restless sleep; Sleeps soundly")
    private String sueno;

    @Schema(description = "Indicates the patient's sexual orientation", example = "Heterosexual; Homosexual; Bisexual; Asexual; Pansexual; Prefer not to say")
    private String sexualidad;

    @Schema(description = "Lists any known patient allergies, or states if there are none", example = "No known allergies; Penicillin; Pollen; Peanuts; Latex")
    private String alergias;

    @Schema(description = "Lists the patient's sports activities or physical exercises", example = "Running, Swimming; Yoga; Weightlifting; Cycling")
    private String actividadesDeportivas;

    @Schema(description = "Other physiological habits or notes", example = "No additional notes")
    private String otros;
}
