package com.amachi.app.core.geography.country.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when a country is updated.
 */
@Getter
public class CountryUpdatedEvent extends DomainEvent {

    private final Long countryId;
    private final String name;

    public CountryUpdatedEvent(Long countryId, String name) {
        super();
        this.countryId = countryId;
        this.name = name;
    }
}
