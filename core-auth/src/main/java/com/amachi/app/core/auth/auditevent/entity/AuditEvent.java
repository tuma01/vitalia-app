package com.amachi.app.core.auth.auditevent.entity;


import com.amachi.app.core.common.enums.AuditEventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "AUT_AUDIT_EVENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private AuditEventType eventType;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = true)
    private String ipAddress;
}

