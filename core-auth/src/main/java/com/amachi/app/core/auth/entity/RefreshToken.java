package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.BaseEntity;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "AUT_REFRESH_TOKEN", uniqueConstraints = {
        @UniqueConstraint(name = "UK_REFRESH_TOKEN_TOKEN", columnNames = "TOKEN")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class RefreshToken extends BaseEntity {

    // ID heredado de BaseEntity

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_REFRESH_TOKEN_USER"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TENANT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_REFRESH_TOKEN_TENANT"))
    private Tenant tenant;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private Instant expiryDate;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Instant createdAt;

    @Builder.Default
    @Column(name = "REVOKED", nullable = false)
    private boolean revoked = false;

    @PrePersist
    public void prePersist() {
        if (createdAt == null)
            createdAt = Instant.now();
    }
}