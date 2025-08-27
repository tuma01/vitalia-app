package com.amachi.app.vitalia.historiamedica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Schema(name = "HistoriaMedica", description = "Schema to hold HistoriaMedica information")

public class HistoriaMedicaDto {

    @Schema(description = "Unique identifier of the medical history", example = "1")
    private Long id;

    @Schema(description = "The diagnosis of the medical history", example = "334L")
    private Long patientId;

    @Schema(description = "The doctor who created the medical history", example = "5L")
    private Long doctorId;

    @Schema(description = "The hospital where the medical history was created", example = "3L")
    private Long hospitalId;

    @Schema(description = "The date of the medical history", example = "2023-10-01")
    private LocalDate date;

    @Schema(description = "The admission date of the medical history", example = "2023-10-01T10:00:00")
    private LocalDateTime admissionDate;

    @Schema(description = "Observations made by the doctor", example = "Patient shows symptoms of flu")
    private String observations;

    @Schema(description = "List of medical consultations associated with the medical history")
    private List<ConsultaMedicaDto> consultasMedicas = new ArrayList<>();

    @Schema(description = "List of current diseases associated with the medical history")
    private List<EnfermedadActualDto> enfermedadesActuales = new ArrayList<>();

    @Schema(description = "List of disease records associated with the medical history")
    private List<RegistroEnfermedadDto> registroEnfermedades = new ArrayList<>();
}
