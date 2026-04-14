package com.amachi.app.core.geography.municipality.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when a new municipality is created.
 */
@Getter
public class MunicipalityCreatedEvent extends DomainEvent {

    private final Long municipalityId;
    private final String name;

    public MunicipalityCreatedEvent(Long municipalityId, String name) {
        super();
        this.municipalityId = municipalityId;
        this.name = name;
    }
}
