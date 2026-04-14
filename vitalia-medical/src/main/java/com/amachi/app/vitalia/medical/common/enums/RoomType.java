package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Professional classification of hospital room categories and comfort levels.
 */
@Schema(description = "Categories for hospital room comfort and care levels")
public enum RoomType {
    @Schema(description = "Standard shared hospital room")
    STANDARD,
    @Schema(description = "Individual private room")
    PRIVATE,
    @Schema(description = "Luxury medical suite with extra amenities")
    SUITE,
    @Schema(description = "Intensive Care Unit (ICU)")
    ICU,
    @Schema(description = "Intermediate Care Unit")
    INTERMEDIATE,
    @Schema(description = "Post-anesthesia recovery room")
    RECOVERY
}
