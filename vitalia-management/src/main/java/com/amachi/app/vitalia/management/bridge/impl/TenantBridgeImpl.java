package com.amachi.app.vitalia.management.bridge.impl;

import com.amachi.app.core.auth.bridge.TenantBridge;
import com.amachi.app.core.auth.exception.AppSecurityException;
import com.amachi.app.core.common.error.ErrorCode;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.enums.PersonType;
import com.amachi.app.core.common.enums.RelationStatus;
import com.amachi.app.core.common.enums.RoleContext;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.entity.PersonTenant;
import com.amachi.app.vitalia.management.util.PersonTypeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantBridgeImpl implements TenantBridge {

    private final TenantRepository tenantRepository;

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
}
