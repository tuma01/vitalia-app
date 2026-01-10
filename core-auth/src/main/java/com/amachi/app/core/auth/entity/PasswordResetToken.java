package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Table(name = "AUT_PASSWORD_RESET_TOKEN", indexes = {
        @Index(name = "IDX_RESET_TOKEN", columnList = "TOKEN")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PasswordResetToken extends BaseEntity {

    // ID heredado de BaseEntity

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
