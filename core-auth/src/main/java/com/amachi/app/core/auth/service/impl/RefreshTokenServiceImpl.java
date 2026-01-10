package com.amachi.app.core.auth.service.impl;

import com.amachi.app.core.auth.entity.RefreshToken;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.exception.TokenException;
import com.amachi.app.core.auth.repository.RefreshTokenRepository;
import com.amachi.app.core.auth.service.RefreshTokenService;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(Long userId, Long tenantId, String token) {
        User userRef = entityManager.getReference(User.class, userId);
        Tenant tenantRef = entityManager.getReference(Tenant.class, tenantId);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRef)
                .tenant(tenantRef)
                .token(token)
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenException("Refresh token expired. Please sign in again.", "REFRESH_TOKEN_EXPIRED");
        }
    }

    @Override
    @Transactional
    public void deleteByUserIdAndTenantId(Long userId, Long tenantId) {
        refreshTokenRepository.deleteByUserIdAndTenantId(userId, tenantId);
    }

    @Override
    @Transactional
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens();
    }

    @Override
    @Transactional
    public void delete(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }

    @Scheduled(cron = "0 0 2 * * ?") // Cada día a las 2 AM
    @Transactional
    public void cleanupExpiredTokens() {
        log.info("🧹 Cleaning up expired refresh tokens");
        deleteExpiredTokens();
    }
}