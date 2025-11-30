package com.amachi.app.vitalia.superadmin.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(name = "TenantConfigDto")
public class TenantConfigDto {
    private String fallbackHeader;
    private Boolean allowLocal;
    private String defaultDomain;
    private String locale;
    private String timezone;
    private Integer maxUsers;
    private Long storageQuotaBytes;
    private Boolean requireEmailVerification;
    private String passwordPolicyJson; // for extensibility
}

