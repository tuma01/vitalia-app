package com.amachi.app.vitalia.tenant.service.impl;

import com.amachi.app.vitalia.authentication.repository.TenantRepository;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    @Override
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Tenant getTenantByCode(String code) {
        return tenantRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado: " + code));
    }
}
