package com.amachi.app.core.geography.province.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when a new province is created.
 */
@Getter
public class ProvinceCreatedEvent extends DomainEvent {

    private final Long provinceId;
    private final String name;

    public ProvinceCreatedEvent(Long provinceId, String name) {
        super();
        this.provinceId = provinceId;
        this.name = name;
    }
}
