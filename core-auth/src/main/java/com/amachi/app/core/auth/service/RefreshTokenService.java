package com.amachi.app.core.auth.service;

import com.amachi.app.core.auth.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId, Long tenantId, String token);

    Optional<RefreshToken> findByToken(String token);

    void verifyExpiration(RefreshToken token);

    void deleteByUserIdAndTenantId(Long userId, Long tenantId);

    void deleteExpiredTokens();

    void delete(RefreshToken token);
}
