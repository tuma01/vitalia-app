package com.amachi.app.vitalia.medical.consultation.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Advanced search filters for Medical Consultations (SaaS Elite Tier).
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class ConsultationSearchDto implements BaseSearchDto {
    private Long id;
    
    @JsonProperty("patientId")
    private Long patientId;
    
    @JsonProperty("doctorId")
    private Long doctorId;
    
    @JsonProperty("medicalHistoryId")
    private Long medicalHistoryId;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("desde")
    private LocalDateTime fromDate;
    
    @JsonProperty("hasta")
    private LocalDateTime toDate;

    @Override
    public Long getId() {
        return id;
    }
}
