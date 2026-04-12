package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Canales de comunicación disponibles para el envío de recordatorios omnicanal.
 */
@Schema(description = "Canal de entrega de la notificación")
public enum ReminderChannel {
    
    @Schema(description = "Mensajería corta móvil")
    SMS,
    
    @Schema(description = "Correo electrónico profesional")
    EMAIL,
    
    @Schema(description = "Mensajería instantánea multiplataforma")
    WHATSAPP,
    
    @Schema(description = "Notificación push a aplicación móvil")
    APP_PUSH,
    
    @Schema(description = "Llamada automatizada de recordatorio")
    VOICE_CALL
}
