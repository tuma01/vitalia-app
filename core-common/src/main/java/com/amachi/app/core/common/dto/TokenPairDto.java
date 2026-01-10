package com.amachi.app.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Resultado del proceso de autenticación: Access + Refresh tokens y expiraciones.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenPairDto {
    private String accessToken;
    private Instant accessTokenExpiresAt;
    private String refreshToken;
    private Instant refreshTokenExpiresAt;
}
