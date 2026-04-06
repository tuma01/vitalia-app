package com.amachi.app.core.geography.country.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Evento disparado cuando se crea un nuevo país en la plataforma.
 */
@Getter
public class CountryCreatedEvent extends DomainEvent {

    private final Long countryId;
    private final String countryIso;

    public CountryCreatedEvent(Long countryId, String countryIso) {
        super();
        this.countryId = countryId;
        this.countryIso = countryIso;
    }
}
