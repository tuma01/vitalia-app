package com.amachi.app.core.geography.address.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when a new address is created.
 */
@Getter
public class AddressCreatedEvent extends DomainEvent {

    private final Long addressId;
    private final String city;

    public AddressCreatedEvent(Long addressId, String city) {
        super();
        this.addressId = addressId;
        this.city = city;
    }
}
