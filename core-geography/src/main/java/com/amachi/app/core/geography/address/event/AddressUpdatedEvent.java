package com.amachi.app.core.geography.address.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when an address is updated.
 */
@Getter
public class AddressUpdatedEvent extends DomainEvent {

    private final Long addressId;
    private final String city;

    public AddressUpdatedEvent(Long addressId, String city) {
        super();
        this.addressId = addressId;
        this.city = city;
    }
}
