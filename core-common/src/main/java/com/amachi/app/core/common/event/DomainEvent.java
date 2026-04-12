package com.amachi.app.core.common.event;

import com.amachi.app.core.common.context.TenantContext;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Clase base para todos los eventos de dominio de Amachi.
 * Incluye contexto cronológico y de multi-tenencia por diseño.
 */
@Getter
public abstract class DomainEvent {

    // 🔥 NEW: Tiempo absoluto del evento
    private final LocalDateTime occurredAt;

    // 🔥 NEW: Contexto del Tenant (Aislamiento en eventos)
    private final String tenantId;

    protected DomainEvent() {
        this.occurredAt = LocalDateTime.now();
        this.tenantId = TenantContext.getTenant();
    }

    protected DomainEvent(LocalDateTime occurredAt, String tenantId) {
        this.occurredAt = occurredAt;
        this.tenantId = tenantId;
    }
}
