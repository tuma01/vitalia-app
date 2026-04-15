package com.amachi.app.core.auth.dto;

import com.amachi.app.core.common.dto.TokenPairDto;
import com.amachi.app.core.common.dto.UserSummaryDto;

/**
 * Respuesta del endpoint de autenticación (contenido ligero para el frontend).
 * (Manual Implementation to resolve Lombok resolution issues)
 */
import lombok.*;

/**
 * Respuesta del endpoint de autenticación (contenido ligero para el frontend).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private TokenPairDto tokens;
    private UserSummaryDto user;

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    
    @Builder.Default
    private String tokenType = "Bearer";
}
