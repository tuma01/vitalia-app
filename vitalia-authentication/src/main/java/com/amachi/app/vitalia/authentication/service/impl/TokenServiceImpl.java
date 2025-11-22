package com.amachi.app.vitalia.authentication.service.impl;

import com.amachi.app.vitalia.authentication.entity.BlacklistedToken;
import com.amachi.app.vitalia.authentication.entity.RefreshToken;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.exception.TokenException;
import com.amachi.app.vitalia.authentication.repository.BlacklistedTokenRepository;
import com.amachi.app.vitalia.authentication.service.JwtService;
import com.amachi.app.vitalia.authentication.service.RefreshTokenService;
import com.amachi.app.vitalia.authentication.service.TokenService;
import com.amachi.app.vitalia.authentication.dto.JwtUserDto;
import com.amachi.app.vitalia.common.dto.TokenPairDto;
import com.amachi.app.vitalia.common.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    @Transactional
    public TokenPairDto generateAndStoreTokenPair(JwtUserDto userDto, User user, Tenant tenant) {
        // Generar par de tokens usando JwtService
        TokenPairDto tokenPair = jwtService.generateTokenPair(userDto);

        // Guardar refresh token en base de datos
        refreshTokenService.createRefreshToken(
                user.getId(),
                tenant.getId(),
                tokenPair.getRefreshToken()
        );

        return tokenPair;
    }

    @Override
    @Transactional
    public TokenPairDto refreshTokenPair(String refreshToken) {
        // Buscar refresh token en base de datos
        Optional<RefreshToken> storedToken = refreshTokenService.findByToken(refreshToken);
        if (storedToken.isEmpty()) {
            throw new TokenException("Refresh token not found", "REFRESH_TOKEN_NOT_FOUND");
        }

        RefreshToken token = storedToken.get();
        refreshTokenService.verifyExpiration(token);

        // Aquí se debería cargar el usuario y tenant para generar nuevo JwtUserDto
        // Por simplicidad, asumimos que tenemos los datos necesarios
        throw new UnsupportedOperationException("Load user and tenant to generate new token pair");
    }

    @Override
    @Transactional
    public void invalidateUserTokens(Long userId, Long tenantId) {
        refreshTokenService.deleteByUserIdAndTenantId(userId, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateRefreshToken(String refreshToken) {
        Optional<RefreshToken> storedToken = refreshTokenService.findByToken(refreshToken);
        if (storedToken.isEmpty()) {
            return false;
        }

        try {
            refreshTokenService.verifyExpiration(storedToken.get());
            return true;
        } catch (TokenException e) {
            return false;
        }
    }

    /**
     * Invalida un JWT agregándolo a la blacklist.
     */
    public void invalidateToken(String jwt) {
        if (jwt == null || jwt.isBlank()) return;

        BlacklistedToken token = BlacklistedToken.builder()
                .token(jwt)
                .blacklistedAt(java.time.LocalDateTime.now())
                .build();

        blacklistedTokenRepository.save(token);
    }

    /**
     * Verifica si un JWT está en la blacklist
     */
    public boolean isTokenBlacklisted(String jwt) {
        return blacklistedTokenRepository.existsByToken(jwt);
    }
}