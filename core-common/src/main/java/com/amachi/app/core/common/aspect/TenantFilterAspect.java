package com.amachi.app.core.common.aspect;

import com.amachi.app.core.common.context.TenantContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.amachi.app.core.common.utils.AppConstants;

import java.util.Optional;

/**
 * Aspecto de Seguridad para Aislamiento Multi-Tenant.
 * Intercepta el inicio de transacciones y activa el filtro de Hibernate
 * para garantizar que solo se vean datos del tenant actual.
 */
@Aspect
@Component
@Slf4j
public class TenantFilterAspect {

    @PersistenceContext
    private EntityManager entityManager;

    // Interceptamos métodos Transaccionales o de Servicios
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional) || execution(* com.amachi.app.vitalia..service..*(..))")
    public void transactionOrServiceExecution() {
    }

    private static final ThreadLocal<Boolean> IN_FILTER_ASPECT = ThreadLocal.withInitial(() -> false);

    @Around("transactionOrServiceExecution()")
    public Object applyTenantFilter(ProceedingJoinPoint pjp) throws Throwable {
        // 🔄 RECURSION GUARD: Prevent infinite loops within the aspect
        if (Boolean.TRUE.equals(IN_FILTER_ASPECT.get())) {
            return pjp.proceed();
        }

        IN_FILTER_ASPECT.set(true);
        try {
            // Obtenemos la sesión de Hibernate
            Session session = entityManager.unwrap(Session.class);

            // 🛡️ INFRASTRUCTURE BYPASS: Do not filter infrastructure queries (Tenant resolution, User lookup)
            String targetClassName = pjp.getSignature().getDeclaringTypeName();
            if (targetClassName.contains("Tenant") || targetClassName.contains("Authentication") || targetClassName.contains("User") || targetClassName.contains("Account")) {
                log.trace("🔓 Tenant Isolation BYPASSED for Infrastructure: {}", targetClassName);
                return pjp.proceed();
            }

            // 🛡️ SECURITY BYPASS: SuperAdmin should NEVER be filtered.
            if (isSuperAdmin()) {
                log.trace("🔓 Tenant Isolation BYPASSED for Super Admin (Global View)");
                return pjp.proceed();
            }

            // Obtenemos el ID del tenant desde el contexto
            Optional<Long> tenantIdOpt = TenantContext.getTenantId();

            if (tenantIdOpt.isPresent()) {
                Long tenantId = tenantIdOpt.get();
                session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
                log.trace("🛡️ Tenant Isolation ACTIVE for TenantID: {}", tenantId);
            } else {
                // Zero Trust Mode: If no tenant context and NOT SuperAdmin,
                // we hide everything to prevent data leakage.
                session.enableFilter("tenantFilter").setParameter("tenantId", -1L);
                log.trace("🔒 Tenant Isolation ACTIVE (Zero Trust Mode) on: {}", targetClassName);
            }

            return pjp.proceed();

        } finally {
            // 🔒 Limpieza obligatoria: Evitar contaminación de sesiones reutilizadas
            try {
                Session session = entityManager.unwrap(Session.class);
                session.disableFilter("tenantFilter");
            } catch (Exception e) {
                // Silently ignore session issues in finally
            }
            IN_FILTER_ASPECT.remove();
        }
    }

    private boolean isSuperAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities() == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> AppConstants.Roles.ROLE_SUPER_ADMIN
                        .equals(grantedAuthority.getAuthority()));
    }
}
