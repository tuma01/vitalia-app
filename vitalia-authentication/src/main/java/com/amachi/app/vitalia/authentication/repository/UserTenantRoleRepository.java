package com.amachi.app.vitalia.authentication.repository;

import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserTenantRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTenantRoleRepository extends JpaRepository<UserTenantRole, Long> {

    @Query("SELECT utr.role.name FROM UserTenantRole utr WHERE utr.user = :user AND utr.tenant.code = :tenantCode AND utr.active = true AND utr.revokedAt IS NULL")
    List<String> findActiveRolesByUserAndTenantCode(@Param("user") User user, @Param("tenantCode") String tenantCode);
}