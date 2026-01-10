package com.amachi.app.vitalia.management.superadmin.dto;


import com.amachi.app.core.domain.tenant.enums.TenantType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(name="TenantUpdateRequest")
public class TenantUpdateRequest {

    @Size(max = 200)
    private String name;

    @Size(max = 1000)
    private String description;

    private TenantType type;

    @Size(max = 100)
    private String fallbackHeader;

    private Boolean allowLocal;

    @Size(max = 200)
    private String defaultDomain;

    private Boolean isActive;
}

