package com.amachi.app.vitalia.superadmin.repository;

import com.amachi.app.vitalia.superadmin.entity.TenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantConfigRepository extends JpaRepository<TenantConfig, Long> {
    Optional<TenantConfig> findByTenant_Code(String tenantCode);
    Optional<TenantConfig> findByTenant_Id(Long tenantId);
}
