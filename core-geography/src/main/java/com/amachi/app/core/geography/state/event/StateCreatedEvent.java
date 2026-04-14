package com.amachi.app.core.geography.state.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when a new state is created.
 */
@Getter
public class StateCreatedEvent extends DomainEvent {

    private final Long stateId;
    private final String name;

    public StateCreatedEvent(Long stateId, String name) {
        super();
        this.stateId = stateId;
        this.name = name;
    }
}
