package com.amachi.app.core.geography.province.event;

import com.amachi.app.core.common.event.DomainEvent;
import lombok.Getter;

/**
 * Event fired when a province is updated.
 */
@Getter
public class ProvinceUpdatedEvent extends DomainEvent {

    private final Long provinceId;
    private final String name;

    public ProvinceUpdatedEvent(Long provinceId, String name) {
        super();
        this.provinceId = provinceId;
        this.name = name;
    }
}
