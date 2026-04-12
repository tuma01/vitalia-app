package com.amachi.app.core.auth.repository;

import com.amachi.app.core.auth.entity.RefreshToken;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CommonRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserIdAndTenantId(Long userId, String tenantId);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < CURRENT_TIMESTAMP")
    void deleteExpiredTokens();

    @Modifying
    void deleteByUserIdAndTenantId(Long userId, String tenantId);

}
