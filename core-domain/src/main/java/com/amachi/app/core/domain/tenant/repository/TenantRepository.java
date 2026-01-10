package com.amachi.app.core.domain.tenant.repository;

import com.amachi.app.core.domain.tenant.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long>, JpaSpecificationExecutor<Tenant> {

    Page<Tenant> findByNameLike(String name, Pageable pageable);
    Optional<Tenant> findByCode(String code);

    boolean existsByCode(String tenantCode);
}
