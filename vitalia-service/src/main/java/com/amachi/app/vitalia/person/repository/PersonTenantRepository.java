package com.amachi.app.vitalia.person.repository;

import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.RoleContext;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.entity.PersonTenant;
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
public interface PersonTenantRepository extends JpaRepository<PersonTenant, Long>, JpaSpecificationExecutor<PersonTenant> {
    Page<PersonTenant> findByTenantId(Long tenantId, Pageable pageable);
    Optional<PersonTenant> findByPersonIdAndTenantId(Long personId, Long tenantId);

    PersonTenant findByCode(String tenantCode);

    Optional<PersonTenant> findByPersonAndTenantAndRoleContext(Person person, Tenant tenant, RoleContext roleContext);
    Optional<PersonTenant> findByNationalHealthIdAndTenant(String nationalHealthId, Tenant tenant);

    // ✅ También útil para tu lógica
    boolean existsByPersonIdAndTenantId(Long personId, Long tenantId);

    // 🔹 DELETE optimizado con query nativa
    @Modifying
    @Transactional
    @Query("DELETE FROM PersonTenant pt WHERE pt.person.id = :personId AND pt.tenant.id = :tenantId")
    void deleteByPersonIdAndTenantId(@Param("personId") Long personId, @Param("tenantId") Long tenantId);

    // 🔹 EXISTS optimizado
    @Query("SELECT COUNT(pt) > 0 FROM PersonTenant pt WHERE pt.person.id = :personId")
    boolean existsByPersonId(@Param("personId") Long personId);

    // 🔹 COUNT para saber en cuántos tenants está
    @Query("SELECT COUNT(pt) FROM PersonTenant pt WHERE pt.person.id = :personId")
    long countByPersonId(@Param("personId") Long personId);

    boolean existsByTenantAndRoleContext(Tenant tenant, RoleContext roleContext);
}
