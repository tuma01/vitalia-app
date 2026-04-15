package com.amachi.app.core.auth.dto;

import com.amachi.app.core.common.enums.RoleContext;
import java.io.Serializable;
import java.util.List;

/**
 * DTO mínimo que provee los claims necesarios para generar JWT.
 * (Manual Implementation to resolve Lombok resolution issues)
 */
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * DTO mínimo que provee los claims necesarios para generar JWT.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserDto implements Serializable {
    private Long userId;
    private String email;
    private String tenantCode;
    private List<String> roles;
    private RoleContext roleContext;
    private String personName;
}
