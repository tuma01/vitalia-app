package com.amachi.app.core.common.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad para gestionar la idempotencia de las peticiones (Stripe Style).
 * Evita el procesamiento duplicado de la misma operación ante reintentos.
 */
@Entity
@Table(name = "IDEMPOTENCY_KEY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyKey {

    @Id
    @Column(name = "ID_KEY", length = 100)
    private String key;

    // 🔥 NEW: Hash del cuerpo de la petición para asegurar igualdad de datos
    @Column(name = "REQUEST_HASH")
    private String requestHash;

    // 🔥 NEW: Respuesta serializada guardada para retornar al cliente en el reintento
    @Lob
    @Column(name = "CACHED_RESPONSE")
    private String response;

    // 🔥 NEW: Aislamiento por Tenant
    @Column(name = "TENANT_ID", nullable = false, length = 50)
    private String tenantId;
}
