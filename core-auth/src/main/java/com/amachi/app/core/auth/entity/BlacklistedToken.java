package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "AUT_BLACKLISTED_TOKEN")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistedToken extends BaseEntity {

    // ID heredado de BaseEntity

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(name = "blacklisted_at", nullable = false)
    private LocalDateTime blacklistedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
}
