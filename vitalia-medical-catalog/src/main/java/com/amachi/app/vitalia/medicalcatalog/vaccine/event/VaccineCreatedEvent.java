package com.amachi.app.vitalia.medicalcatalog.vaccine.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Event published when a new Vaccine is created in the catalog.
 */
@Getter
@RequiredArgsConstructor
public class VaccineCreatedEvent extends DomainEvent {

    private final Long vaccineId;
    private final String code;
    private final String name;

}
