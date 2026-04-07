package com.amachi.app.core.management.tenantconfig.repository;

import com.amachi.app.core.management.tenantconfig.entity.TenantConfig;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantConfigRepository extends CommonRepository<TenantConfig, Long> {
    Optional<TenantConfig> findByTenant_Code(String tenantCode);
    Optional<TenantConfig> findByTenant_Id(Long tenantId);
    boolean existsByTenant_Id(Long tenantId);
}
