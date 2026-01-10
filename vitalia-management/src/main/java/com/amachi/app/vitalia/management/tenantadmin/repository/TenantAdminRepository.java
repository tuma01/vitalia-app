package com.amachi.app.vitalia.management.tenantadmin.repository;

import com.amachi.app.core.geography.departamento.entity.Departamento;
import com.amachi.app.vitalia.management.tenantadmin.entity.TenantAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantAdminRepository extends JpaRepository<TenantAdmin, Long>, JpaSpecificationExecutor<TenantAdmin> {
    long countByTenantId(Long tenantId);
}
