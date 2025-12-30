package com.amachi.app.vitalia.authentication.service.scheduler;

import com.amachi.app.vitalia.authentication.repository.PasswordResetTokenRepository;
import com.amachi.app.vitalia.authentication.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupService {

    private final TokenService tokenService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    // Ejecutar cada semana (7 días)
    @Scheduled(cron = "0 0 0 * * SUN") // Cada domingo a las 00:00
    @Transactional
    public void cleanupExpiredTokens() {
        log.info("🧹 Starting token cleanup job...");
        LocalDateTime now = LocalDateTime.now();
        Instant nowInstant = Instant.now();

        try {
            // 1. Limpiar JWTs invalidados (Blacklist)
            tokenService.deleteExpiredTokens(now);
            log.info("✅ Blacklisted tokens cleaned.");

            // 2. Limpiar tokens de reset de password expirados
            passwordResetTokenRepository.deleteByExpirationDateBefore(nowInstant);
            log.info("✅ Expired password reset tokens cleaned.");

            log.info("✅ Token cleanup completed successfully.");
        } catch (Exception e) {
            log.error("❌ Failed to cleanup tokens", e);
        }
    }
}
