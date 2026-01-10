package com.amachi.app.core.domain.repository;

import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.enums.RoleContext;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.entity.PersonTenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PersonTenantRepository
        extends JpaRepository<PersonTenant, Long>, JpaSpecificationExecutor<PersonTenant> {
    Page<PersonTenant> findByTenantId(Long tenantId, Pageable pageable);

    Optional<PersonTenant> findByPersonIdAndTenantId(Long personId, Long tenantId);

    Optional<PersonTenant> findByPersonAndTenant(Person person, Tenant tenant);

    // PersonTenant findByCode(String tenantCode);

    Optional<PersonTenant> findByPersonAndTenantAndRoleContext(Person person, Tenant tenant, RoleContext roleContext);

    Optional<PersonTenant> findByNationalHealthIdAndTenant(String nationalHealthId, Tenant tenant);

    // Γ£à Tambi├⌐n ├║til para tu l├│gica
    boolean existsByPersonAndTenant(Person person, Tenant tenant);

    // ≡ƒö╣ DELETE optimizado con query nativa
    @Modifying
    @Transactional
    @Query("DELETE FROM PersonTenant pt WHERE pt.person = :person AND pt.tenant = :tenant")
    void deleteByPersonAndTenant(@Param("person") Person person, @Param("tenant") Tenant tenant);

    // ≡ƒö╣ EXISTS optimizado
    @Query("SELECT COUNT(pt) > 0 FROM PersonTenant pt WHERE pt.person.id = :personId")
    boolean existsByPersonId(@Param("personId") Long personId);

    // ≡ƒö╣ COUNT para saber en cu├íntos tenants est├í
    @Query("SELECT COUNT(pt) FROM PersonTenant pt WHERE pt.person.id = :personId")
    long countByPersonId(@Param("personId") Long personId);

    boolean existsByTenantAndRoleContext(Tenant tenant, RoleContext roleContext);
}
