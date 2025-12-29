package com.amachi.app.vitalia.superadmin.validation;

import com.amachi.app.vitalia.tenant.repository.TenantRepository;
import com.amachi.app.vitalia.authentication.repository.UserRepository;
import com.amachi.app.vitalia.superadmin.dto.TenantCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantValidator {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;

    public void validateCreate(TenantCreateRequest req) {
        if (tenantRepository.existsByCode(req.getCode())) {
            throw new IllegalArgumentException("tenant.code.exists");
        }
        if (userRepository.existsByEmail(req.getAdminEmail())) {
            throw new IllegalArgumentException("admin.email.exists");
        }
    }
}

