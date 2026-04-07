package com.amachi.app.core.management.tenantadmin.repository;

import com.amachi.app.core.common.enums.TenantAdminLevel;
import com.amachi.app.core.management.tenantadmin.entity.TenantAdmin;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantAdminRepository extends CommonRepository<TenantAdmin, Long> {
    long countByTenantIdAndUserEnabledTrue(Long tenantId);
    long countByTenantIdAndAdminLevelAndUserEnabledTrue(Long tenantId, TenantAdminLevel level);
}
