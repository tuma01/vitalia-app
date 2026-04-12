package com.amachi.app.core.domain.tenant.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends CommonRepository<Tenant, Long> {

    @Override
    @EntityGraph(attributePaths = { "theme" })
    Page<Tenant> findAll(Specification<Tenant> spec, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = { "theme" })
    List<Tenant> findAll(Sort sort);

    Page<Tenant> findByNameLike(String name, Pageable pageable);

    Optional<Tenant> findByCode(String code);

    boolean existsByCode(String tenantCode);
}
