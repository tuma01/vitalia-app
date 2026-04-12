package com.amachi.app.vitalia.medical.history.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * ≡ƒôô Solicitud para registrar una observaci├│n o medici├│n cl├¡nica (FHIR Observation).
 */
@NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Solicitud para añadir medición clínica")
public class ObservationRequest {

    @NotNull(message = "El c├│digo de observaci├│n (LOINC) es obligatorio")
    @Schema(description = "C├│digo internacional (LOINC, SNOMED, etc.)", example = "8480-6")
    private String code;

    @NotNull(message = "El nombre de la medici├│n es obligatorio")
    @Schema(description = "Descripci├│n legible del hallazgo", example = "Tensi├│n Arterial")
    private String name;

    @NotNull(message = "El valor del hallazgo es obligatorio")
    @Schema(description = "Valor num├⌐rico o textual", example = "120/80")
    private String value;

    @Schema(description = "Unidad de medida (UCUM)", example = "mmHg")
    private String unit;

    @Schema(description = "Interpretaci├│n (HIGH, LOW, NORMAL)", example = "NORMAL")
    private String interpretation;

    @Schema(description = "Notas técnicas del facultativo")
    private String notes;

    // Manual Accessors
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public String getInterpretation() { return interpretation; }
    public void setInterpretation(String interpretation) { this.interpretation = interpretation; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
