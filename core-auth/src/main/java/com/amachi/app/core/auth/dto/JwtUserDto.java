package com.amachi.app.core.auth.dto;

import com.amachi.app.core.common.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * DTO mínimo que provee los claims necesarios para generar JWT.
 * Usado por JwtService en el módulo vitalia-security.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUserDto implements Serializable {
    private Long userId;
    private String email;
    private String tenantCode;
    private List<String> roles;
    private PersonType personType;
    private String personName;
}
