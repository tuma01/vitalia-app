package com.amachi.app.core.auth.auditevent.service.impl;

import com.amachi.app.core.auth.auditevent.entity.AuditEvent;
import com.amachi.app.core.auth.auditevent.repository.AuditEventRepository;
import com.amachi.app.core.auth.auditevent.service.AuditService;
import com.amachi.app.core.common.enums.AuditEventType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final AuditEventRepository auditEventRepository;

    @Override
    @Transactional
    public void registerEvent(AuditEventType type,
                              Long userId,
                              Long tenantId,
                              String message) {
        registerEvent(type, userId, tenantId, message, null);
    }

    @Override
    @Transactional
    public void registerEvent(AuditEventType type,
                              Long userId,
                              Long tenantId,
                              String message,
                              String ipAddress) {

        try {
            AuditEvent event = AuditEvent.builder()
                    .eventType(type)
                    .userId(userId)
                    .tenantId(tenantId)
                    .message(message)
                    .timestamp(LocalDateTime.now())
                    .ipAddress(ipAddress)
                    .build();

            auditEventRepository.save(event);

        } catch (Exception ex) {
            // No romper el flujo de autenticación jamás por auditoría
            log.warn("⚠️ Error registrando auditoría: {}", ex.getMessage());
        }
    }
}
