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
    private TokenPairDto tokens;
    private UserSummaryDto user;
}
