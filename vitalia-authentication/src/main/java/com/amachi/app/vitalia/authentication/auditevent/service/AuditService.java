package com.amachi.app.vitalia.authentication.auditevent.service;

import com.amachi.app.vitalia.common.enums.AuditEventType;

public interface AuditService {

    void registerEvent(
            AuditEventType type,
            Long userId,
            Long tenantId,
            String message
    );

    void registerEvent(
            AuditEventType type,
            Long userId,
            Long tenantId,
            String message,
            String ip
    );
}
