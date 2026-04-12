package com.amachi.app.vitalia.medical.appointment.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.core.common.enums.AppointmentStatus;
import com.amachi.app.vitalia.medical.common.enums.AppointmentSource;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.OffsetDateTime;

/**
 * Advanced search filters for medical appointment queries (SaaS Elite Tier).
 * Uses OffsetDateTime for multi-region timezone support.
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class AppointmentSearchDto implements BaseSearchDto {

    @Schema(description = "Unique ID of the appointment")
    private Long id;

    @Schema(description = "Filter by patient ID")
    private Long patientId;

    @Schema(description = "Filter by doctor ID")
    private Long doctorId;

    @Schema(description = "Filter by room ID")
    private Long roomId;

    @Schema(description = "Filter by appointment workflow status")
    private AppointmentStatus status;

    @Schema(description = "Filter by appointment source channel")
    private AppointmentSource source;

    @Schema(description = "Filter no-show appointments")
    private Boolean noShow;

    @Schema(description = "Filter by medical specialty")
    private String specialty;

    @Schema(description = "Range filter: appointments starting on or after this datetime", example = "2026-04-01T00:00:00Z")
    private OffsetDateTime startFrom;

    @Schema(description = "Range filter: appointments starting on or before this datetime", example = "2026-04-30T23:59:59Z")
    private OffsetDateTime startTo;

    @Override
    public Long getId() {
        return id;
    }
}
