package com.amachi.app.vitalia.authentication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "PASSWORD_RESET_TOKEN", indexes = {
        @Index(name = "IDX_RESET_TOKEN", columnList = "TOKEN")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOKEN", nullable = false, unique = true, length = 255)
    private String token;

    @Column(name = "EXPIRATION_DATE", nullable = false)
    private Instant expirationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_RESET_TOKEN_USER"))
    private User user;

    @Column(name = "USED", nullable = false)
    @Builder.Default
    private boolean used = false;

    public boolean isExpired() {
        return expirationDate.isBefore(Instant.now());
    }

    public boolean isInvalid() {
        return used || isExpired();
    }
}
