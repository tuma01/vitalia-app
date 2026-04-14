package com.amachi.app.core.geography.country.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when a new country is created.
 */
@Getter
public class CountryCreatedEvent extends DomainEvent {

    private final Long countryId;
    private final String name;

    public CountryCreatedEvent(Long countryId, String name) {
        super();
        this.countryId = countryId;
        this.name = name;
    }
}
