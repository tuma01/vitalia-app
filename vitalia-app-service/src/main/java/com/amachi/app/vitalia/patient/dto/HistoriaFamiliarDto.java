package com.amachi.app.vitalia.patient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Schema(name = "HistoriaFamiliar", description = "Represents the patient's family medical history.")
public class HistoriaFamiliarDto {

    @Schema(description = "Unique identifier for the family medical history record.")
    private Long id;

    @Schema(description = "Detailed health information and medical conditions of the patient's mother.",
            example = "Mother: Healthy, no significant medical history. Or: Mother: Diabetes type 2, Hypertension.")
    private String informacionSaludMadre;

    @Schema(description = "Detailed health information and medical conditions of the patient's father.",
            example = "Father: Healthy, no significant medical history. Or: Father: Coronary artery disease, High cholesterol.")
    private String informacionSaludPadre;

    @Schema(description = "Detailed health information and medical conditions of the patient's siblings.",
            example = "Siblings: One brother with asthma, one sister healthy. Or: No siblings.")
    private String informacionSaludHermanos;

    @Schema(description = "Detailed health information and medical conditions of the patient's grandparents (both maternal and paternal).",
            example = "Maternal Grandmother: Alzheimer's disease; Paternal Grandfather: Arthritis.")
    private String informacionSaludAbuelos;

    @Schema(description = "List of inherited or hereditary diseases present in the family.",
            example = "[\"Cystic Fibrosis\", \"Hemophilia\", \"Huntington's Disease\"]")
    private List<String> enfermedadesHereditarias;

    @Schema(description = "History of cardiac or heart-related diseases within the patient's family.",
            example = "Family history of heart attacks and strokes; No known family history of heart disease.")
    private String historialEnfermedadesCardiacasFamiliares;

    @Schema(description = "History of mental health conditions or neurological disorders within the patient's family.",
            example = "Family history of depression and anxiety; No known family history of mental illness.")
    private String historialEnfermedadesMentalesFamiliares;

    @Schema(description = "Timestamp indicating when this family medical history record was initially created.",
            example = "2024-01-15T10:30:00Z")
    private LocalDateTime fechaRegistroHistoriaFamiliar;

    @Schema(description = "Timestamp indicating the last time this family medical history record was updated.",
            example = "2024-03-20T14:45:00Z")
    private LocalDateTime fechaActualizacionHistoriaFamiliar;

    @Schema(description = "Information about the patient's children, including their health status or any relevant conditions.",
            example = "Son (age 5): Healthy; Daughter (age 2): Has mild eczema. Or: No children.")
    private String informacionSaludHijos;

    @Schema(description = "Information about other significant blood relatives (aunts, uncles, cousins, etc.) and their relevant health conditions.",
            example = "Maternal Aunt: Diagnosed with breast cancer at age 55; Paternal Uncle: No significant health issues.")
    private String informacionSaludOtrosParientes;

    @Schema(description = "Indicates if there's a family history of diabetes (e.g., Type 1, Type 2, Gestational).",
            example = "Yes, Type 2 in father and paternal grandmother; No known family history.")
    private String historialDiabetesFamiliar;

    @Schema(description = "Indicates if there's a family history of cancer, specifying types if known.",
            example = "Yes, Breast cancer in maternal aunt, Colon cancer in paternal grandfather; No known family history.")
    private String historialCancerFamiliar;

    @Schema(description = "Ethnicity or ancestral background of the family, as some genetic conditions are more prevalent in certain ethnic groups.",
            example = "Caucasian, Hispanic, African American, East Asian")
    private String etniaFamilia;

    @Schema(description = "Any notes or additional context regarding the family medical history that doesn't fit into specific fields.",
            example = "Patient adopted, limited family history information available; Some information is self-reported and unconfirmed.")
    private String notasAdicionales;
}