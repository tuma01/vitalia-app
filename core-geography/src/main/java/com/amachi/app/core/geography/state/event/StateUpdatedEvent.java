package com.amachi.app.core.geography.state.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when a state is updated.
 */
@Getter
public class StateUpdatedEvent extends DomainEvent {

    private final Long stateId;
    private final String name;

    public StateUpdatedEvent(Long stateId, String name) {
        super();
        this.stateId = stateId;
        this.name = name;
    }
}
