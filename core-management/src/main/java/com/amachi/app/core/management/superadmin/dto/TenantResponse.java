package com.amachi.app.core.management.superadmin.dto;

import com.amachi.app.core.common.enums.TenantType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "TenantResponse")

//TODO a borrar cuando se use el TenantDto
public class TenantResponse {
    private Long tenantId;
    private String code;
    private String name;
    private TenantType type;
    private String description;
    private Boolean isActive;
    private String fallbackHeader;
    private Boolean allowLocal;
    private String defaultDomain;
    private LocalDateTime createdAt;
}

