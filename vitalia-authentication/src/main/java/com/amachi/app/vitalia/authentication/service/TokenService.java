package com.amachi.app.vitalia.authentication.service;

import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.dto.JwtUserDto;
import com.amachi.app.vitalia.common.dto.TokenPairDto;
import com.amachi.app.vitalia.common.entity.Tenant;

public interface TokenService {
    TokenPairDto generateAndStoreTokenPair(JwtUserDto userDto, User user, Tenant tenant);

    TokenPairDto refreshTokenPair(String refreshToken);

    void invalidateUserTokens(Long userId, Long tenantId);

    boolean validateRefreshToken(String refreshToken);

    void invalidateToken(String jwt);

    boolean isTokenBlacklisted(String jwt);

    void deleteExpiredTokens(java.time.LocalDateTime now);
}
