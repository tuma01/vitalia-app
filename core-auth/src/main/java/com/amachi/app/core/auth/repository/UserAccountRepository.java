package com.amachi.app.core.auth.repository;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.entity.UserAccount;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repositorio para la entidad UserAccount.
 * Es crucial en la arquitectura Multi-Tenant ya que vincula a User, Person,
 * Tenant y Roles.
 * Ubicado en vitalia-app-service.
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long>, JpaSpecificationExecutor<UserAccount> {

    /**
     * Busca una UserAccount por el email del usuario y el código del tenant.
     * Es la consulta principal utilizada durante el proceso de autenticación.
     * 
     * @param email      Email del usuario.
     * @param tenantCode Código del tenant (ej: "HSP001").
     * @return Optional de UserAccount.
     */
    @Query("""
                SELECT ua
                FROM UserAccount ua
                JOIN FETCH ua.user u
                JOIN FETCH ua.tenant t
                WHERE u.email = :email
                AND t.code = :tenantCode
            """)
    Optional<UserAccount> findByUserEmailAndTenantCode(
            @Param("email") String email,
            @Param("tenantCode") String tenantCode);

    /**
     * Busca la UserAccount activa basada en el ID del usuario y el ID del tenant.
     * Es crucial para establecer el contexto de sesión después del login (ej.
     * obtener el perfil).
     * 
     * @param userId   ID del usuario (desde JWT/security context).
     * @param tenantId ID del tenant (desde TenantContext).
     * @return Optional con la UserAccount.
     */
    Optional<UserAccount> findByUserIdAndTenantId(Long userId, Long tenantId);

    /**
     * Verifica si existe una relación entre un Usuario y un Tenant, útil para la
     * gestión de acceso.
     * 
     * @param user   El usuario.
     * @param tenant El tenant.
     * @return true si la cuenta de usuario existe.
     */
    boolean existsByUserAndTenant(User user, Tenant tenant);

    /**
     * Busca todas las cuentas asociadas a un usuario, útil para la selección de
     * Tenant después del login.
     * 
     * @param userId ID del usuario.
     * @return Conjunto de UserAccount.
     */
    Set<UserAccount> findByUserId(Long userId);

    Optional<UserAccount> findByUserAndTenantCode(User user, String tenantCode);

    Optional<UserAccount> findByPerson(com.amachi.app.core.domain.entity.Person person);

    java.util.List<UserAccount> findByUser(com.amachi.app.core.auth.entity.User user);

    // Encuentra todos los UserAccount de un tenant específico
    List<UserAccount> findByTenantId(Long tenantId);

    // Opcional: filtrar por rol si quieres precisión
    // @Query("SELECT ua FROM UserAccount ua JOIN ua.roles r WHERE ua.tenant.id =
    // :tenantId AND r.name = :roleName")
    // List<UserAccount> findByTenantIdAndRoleName(@Param("tenantId") Long tenantId,
    // @Param("roleName") String roleName);
}