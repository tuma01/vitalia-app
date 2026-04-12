package com.amachi.app.vitalia.medical.common.enums;

/**
 * Estado del flujo de una prescripción médica (FHIR MedicationRequest Status).
 */
public enum MedicationRequestStatus {
    ACTIVE,
    ON_HOLD,
    CANCELLED,
    COMPLETED,
    ENTERED_IN_ERROR,
    STOPPED,
    DRAFT,
    UNKNOWN
}
