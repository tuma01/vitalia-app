package com.amachi.app.core.auth.dto;

import com.amachi.app.core.common.dto.TokenPairDto;
import com.amachi.app.core.common.dto.UserSummaryDto;
import lombok.*;

/**
 * Respuesta del endpoint de autenticación (contenido ligero para el frontend).
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    // 🔥 New Structure for Enterprise Tier
    private TokenPairDto tokens;
    private UserSummaryDto user;

    // 🛡️ Legacy Restore (Para evitar romper el Frontend actual)
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    @Builder.Default
    private String tokenType = "Bearer";
}
