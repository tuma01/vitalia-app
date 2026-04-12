package com.amachi.app.vitalia.medical.history.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Event published when a new Medical History (EHR) is finalized/created.
 */
@Getter
@RequiredArgsConstructor
public class MedicalHistoryCreatedEvent extends DomainEvent {
    private final Long id;
    private final String historyNumber;
    private final Long patientId;
}
