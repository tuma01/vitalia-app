package com.amachi.app.core.geography.municipio.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Evento disparado cuando se crea un nuevo municipio.
 */
@Getter
public class MunicipioCreatedEvent extends DomainEvent {

    private final Long municipioId;
    private final String nombre;

    public MunicipioCreatedEvent(Long municipioId, String nombre) {
        super();
        this.municipioId = municipioId;
        this.nombre = nombre;
    }
}
