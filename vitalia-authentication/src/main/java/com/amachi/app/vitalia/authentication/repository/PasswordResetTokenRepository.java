package com.amachi.app.vitalia.authentication.repository;

import com.amachi.app.vitalia.authentication.entity.PasswordResetToken;
import com.amachi.app.vitalia.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(User user);
}
