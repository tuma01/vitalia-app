package com.amachi.app.core.management.bridge.impl;

import com.amachi.app.core.auth.bridge.TenantBridge;
import com.amachi.app.core.auth.exception.AppSecurityException;
import com.amachi.app.core.common.enums.RoleContext;
import com.amachi.app.core.common.error.ErrorCode;
import com.amachi.app.core.domain.repository.PersonTenantRepository;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantBridgeImpl implements TenantBridge {

    private final TenantRepository tenantRepository;
    private final PersonTenantRepository personTenantRepository;

    @Override
    public Tenant findByCode(String tenantCode) {
        return tenantRepository.findByCode(tenantCode)
                .orElseThrow(() -> {
                    log.error("❌ Tenant [{}] no encontrado en DB", tenantCode);
                    return new AppSecurityException(
                            ErrorCode.SEC_TENANT_NOT_FOUND,
                            "security.tenant.not_found",
                            tenantCode);
                });
    }

    @Override
    public Tenant findById(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    log.error("❌ Tenant ID [{}] no encontrado en DB", tenantId);
                    return new AppSecurityException(
                            ErrorCode.SEC_TENANT_NOT_FOUND,
                            "security.tenant.id_not_found",
                            tenantId.toString());
                });
    }

    @Override
    public RoleContext findRoleContext(Long personId, String tenantCode) {
        return personTenantRepository.findByPersonIdAndTenantCode(personId, tenantCode)
                .map(com.amachi.app.core.domain.entity.PersonTenant::getRoleContext)
                .orElse(null);
    }
}
