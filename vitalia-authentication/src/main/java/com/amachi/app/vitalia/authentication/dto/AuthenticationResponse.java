package com.amachi.app.vitalia.authentication.dto;

import com.amachi.app.vitalia.common.dto.TokenPairDto;
import com.amachi.app.vitalia.common.dto.UserSummaryDto;
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
