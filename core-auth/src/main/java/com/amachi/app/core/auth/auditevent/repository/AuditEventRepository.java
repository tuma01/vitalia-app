package com.amachi.app.core.auth.auditevent.repository;

import com.amachi.app.core.auth.auditevent.entity.AuditEvent;
import com.amachi.app.core.common.enums.AuditEventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {

    List<AuditEvent> findByTenantId(Long tenantId);

    List<AuditEvent> findByUserId(Long userId);

    List<AuditEvent> findByTenantIdAndEventType(Long tenantId, AuditEventType type);
}

