package com.amachi.app.vitalia.bridge.impl;

import com.amachi.app.vitalia.authentication.bridge.TenantBridge;
import com.amachi.app.vitalia.authentication.exception.AppSecurityException;
import com.amachi.app.vitalia.common.error.ErrorCode;
import com.amachi.app.vitalia.tenant.repository.TenantRepository;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.common.enums.RelationStatus;
import com.amachi.app.vitalia.common.enums.RoleContext;
import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.entity.PersonTenant;
import com.amachi.app.vitalia.util.PersonTypeValidator;
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
