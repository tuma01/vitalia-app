package com.amachi.app.core.geography.provincia.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Evento disparado cuando se crea una nueva provincia.
 */
@Getter
public class ProvinciaCreatedEvent extends DomainEvent {

    private final Long provinciaId;
    private final String nombre;

    public ProvinciaCreatedEvent(Long provinciaId, String nombre) {
        super();
        this.provinciaId = provinciaId;
        this.nombre = nombre;
    }
}
