package com.amachi.app.vitalia.medical.history.dto.timeline;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.OffsetDateTime;

/**
 * Registro unificado de un "Acto Clínico" en la línea de tiempo del paciente.
 * Consolida Encuentros, Diagnósticos, Mediciones y Prescripciones en una sola vista cronológica.
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "DTO de evento para línea de tiempo clínica unificada")
public class ClinicalEventDto {

    @Schema(description = "Tipo de evento (ENCOUNTER, CONDITION, OBSERVATION, MEDICATION)")
    private String type;

    @Schema(description = "ID único del recurso original")
    private Long resourceId;

    @Schema(description = "Título legible (ej: Consulta Ambulatoria, Diagnóstico Diabetes)")
    private String title;

    @Schema(description = "Descripción o valor (ej: Notas médicas, 120/80 mmHg)")
    private String content;

    @Schema(description = "Nombre del facultativo responsable")
    private String practitionerName;

    @Schema(description = "Fecha y hora efectiva del evento")
    private OffsetDateTime effectiveDateTime;

    @Schema(description = "Estado actual del recurso (COMPLETED, ACTIVE, etc.)")
    private String status;

    @Schema(description = "ID del encuentro asociado para navegación")
    private Long encounterId;
    
    // Manual Setters and Getters to bypass Lombok issues
    public void setType(String type) { this.type = type; }
    public String getType() { return type; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
    public Long getResourceId() { return resourceId; }
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void setContent(String content) { this.content = content; }
    public String getContent() { return content; }
    public void setPractitionerName(String practitionerName) { this.practitionerName = practitionerName; }
    public String getPractitionerName() { return practitionerName; }
    public void setEffectiveDateTime(OffsetDateTime effectiveDateTime) { this.effectiveDateTime = effectiveDateTime; }
    public OffsetDateTime getEffectiveDateTime() { return effectiveDateTime; }
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
    public void setEncounterId(Long encounterId) { this.encounterId = encounterId; }
    public Long getEncounterId() { return encounterId; }
}
