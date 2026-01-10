package com.amachi.app.vitalia.management.tenantconfig.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

@Schema(name = "TenantConfig", description = "Schema to hold TenantConfig information")
public class TenantConfigDto {
    @Schema(description = "Id TenantConfig", example = "1")
    private Long id;

    @Schema(description = "Tenant ID associated with the TenantConfig", example = "1")
    private Long tenantId;

    @Schema(description = "Header to be used for tenant identification", example = "X-Tenant-ID")
    private String fallbackHeader;

    @Schema(description = "Allow local logins", example = "true")
    private Boolean allowLocal;

    @Schema(description = "Default domain for the tenant", example = "example.com")
    private String defaultDomain;

    @Schema(description = "Locale setting for the tenant", example = "en-US")
    private String locale;

    @Schema(description = "Timezone setting for the tenant", example = "America/New_York")
    private String timezone;

    @Schema(description = "Maximum number of users allowed for the tenant", example = "100")
    private Integer maxUsers;

    @Schema(description = "Storage quota in bytes for the tenant", example = "1073741824")
    private Long storageQuotaBytes;

    @Schema(description = "Require email verification for new users", example = "true")
    private Boolean requireEmailVerification;

    @Schema(description = "Password policy in JSON format for extensibility", example = "{\"minLength\":8,\"requireSpecialChar\":true}")
    private String passwordPolicyJson; // for extensibility

    @Schema(description = "Extra configuration in JSON format for future use", example = "{\"customSetting\":\"value\"}")
    private String extraJson;
}

