package com.amachi.app.core.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Estados de entrega de recordatorios (Omnicanal).
 */
@Schema(description = "Trazabilidad de entrega de recordatorios")
public enum ReminderStatus {
    @Schema(description = "Pendiente por enviar (cron-job)")
    PENDING,
    @Schema(description = "Enviado exitosamente a la pasarela")
    SENT,
    @Schema(description = "Usuario confirmo lectura (Webhook)")
    READ,
    @Schema(description = "Error en el envio (Provider error)")
    FAILED
}
