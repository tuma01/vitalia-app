package com.amachi.app.vitalia.tenantadmin.repository;

import com.amachi.app.vitalia.geography.departamento.entity.Departamento;
import com.amachi.app.vitalia.tenantadmin.entity.TenantAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantAdminRepository extends JpaRepository<TenantAdmin, Long>, JpaSpecificationExecutor<TenantAdmin> {
    long countByTenantId(Long tenantId);
}
