package com.amachi.app.core.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publicador central para eventos de dominio.
 * Facilita el desacoplamiento de side-effects en la plataforma.
 */
@Component
@RequiredArgsConstructor
public class DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    /**
     * 🔥 NEW: Publica un evento de dominio (asíncrono o síncrono según @EventListener).
     * Reemplazable por Kafka/RabbitMQ en el futuro sin cambiar servicios.
     */
    public void publish(DomainEvent event) {
        if (event != null) {
            publisher.publishEvent(event);
        }
    }
}
