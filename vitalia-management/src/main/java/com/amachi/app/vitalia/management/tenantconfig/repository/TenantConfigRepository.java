package com.amachi.app.vitalia.management.tenantconfig.repository;

import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.vitalia.management.tenantconfig.entity.TenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TenantConfigRepository extends JpaRepository<TenantConfig, Long>, JpaSpecificationExecutor<TenantConfig> {
    Optional<TenantConfig> findByTenant_Code(String tenantCode);
    Optional<TenantConfig> findByTenant_Id(Long tenantId);
}
