package com.amachi.app.core.geography.municipality.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when a municipality is updated.
 */
@Getter
public class MunicipalityUpdatedEvent extends DomainEvent {

    private final Long municipalityId;
    private final String name;

    public MunicipalityUpdatedEvent(Long municipalityId, String name) {
        super();
        this.municipalityId = municipalityId;
        this.name = name;
    }
}
