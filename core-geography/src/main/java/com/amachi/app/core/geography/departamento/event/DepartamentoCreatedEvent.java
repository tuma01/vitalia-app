package com.amachi.app.core.geography.departamento.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Evento disparado cuando se crea un nuevo departamento.
 */
@Getter
public class DepartamentoCreatedEvent extends DomainEvent {

    private final Long departamentoId;
    private final String nombre;

    public DepartamentoCreatedEvent(Long departamentoId, String nombre) {
        super();
        this.departamentoId = departamentoId;
        this.nombre = nombre;
    }
}
