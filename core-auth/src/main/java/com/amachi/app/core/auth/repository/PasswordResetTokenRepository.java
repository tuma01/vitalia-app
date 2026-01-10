package com.amachi.app.core.auth.repository;

import com.amachi.app.core.auth.entity.PasswordResetToken;
import com.amachi.app.core.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUser(User user);

    void deleteByExpirationDateBefore(java.time.Instant now);
}
