package com.amachi.app.core.auth.service;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.dto.JwtUserDto;
import com.amachi.app.core.common.dto.TokenPairDto;
import com.amachi.app.core.domain.tenant.entity.Tenant;

public interface TokenService {
    TokenPairDto generateAndStoreTokenPair(JwtUserDto userDto, User user, Tenant tenant);

    TokenPairDto refreshTokenPair(String refreshToken);

    void invalidateUserTokens(Long userId, Long tenantId);

    boolean validateRefreshToken(String refreshToken);

    void invalidateToken(String jwt);

    boolean isTokenBlacklisted(String jwt);

    void deleteExpiredTokens(java.time.LocalDateTime now);
}
