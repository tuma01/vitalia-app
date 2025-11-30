package com.amachi.app.vitalia.authentication.repository;

import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserTenantRole;
import com.amachi.app.vitalia.common.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTenantRoleRepository extends JpaRepository<UserTenantRole, Long> {

//    @Query("SELECT utr.role.name FROM UserTenantRole utr WHERE utr.user = :user AND utr.tenant.code = :tenantCode AND utr.active = true AND utr.revokedAt IS NULL")
//    List<String> findActiveRolesByUserAndTenantCode(@Param("user") User user, @Param("tenantCode") String tenantCode);

    // Nuevo método para buscar roles activos por usuario (sin filtrar por tenant)
//    @Query("SELECT utr.role.name FROM UserTenantRole utr WHERE utr.user = :user AND utr.active = true AND utr.revokedAt IS NULL")
//    List<String> findActiveRolesByUser(@Param("user") User user);

    // Método adicional para verificar directamente si es SUPER_ADMIN
    default boolean isUserSuperAdmin(User user) {
        return findActiveRolesByUser(user).stream()
                .anyMatch(r -> r.equalsIgnoreCase("SUPER_ADMIN") || r.equalsIgnoreCase("ROLE_SUPER_ADMIN"));
    }

    boolean existsByUserAndTenantAndRole(User user, Tenant tenant, Role role);

    @Query("select r.role.name from UserTenantRole r where r.user = :user and r.tenant.code = :tenantCode and r.active = true")
    List<String> findActiveRolesByUserAndTenantCode(@Param("user") User user, @Param("tenantCode") String tenantCode);

    @Query("select r.role.name from UserTenantRole r where r.user = :user and r.active = true")
    List<String> findActiveRolesByUser(@Param("user") User user);

    @Query("""
        SELECT r.name
        FROM UserTenantRole utr
        JOIN utr.role r
        WHERE utr.user.id = :userId
          AND utr.tenant.id = :tenantId
          AND utr.active = true
    """)
    List<String> findActiveRoleNamesByUserAndTenant(@Param("userId") Long userId,
                                                    @Param("tenantId") Long tenantId);
}