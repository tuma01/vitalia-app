package com.amachi.app.core.common.aspect;

import com.amachi.app.core.common.context.TenantContext;
import com.amachi.app.core.common.utils.AppConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Aspecto de Seguridad para Aislamiento Multi-Tenant.
 * Fixed: Uniformidad de tipos (TENANT_ID=String) conforme a arquitectura original.
 */
@Aspect
@Component
@Slf4j
public class TenantFilterAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional) || execution(* com.amachi.app.vitalia..service..*(..))")
    public void transactionOrServiceExecution() {
    }

    private static final ThreadLocal<Boolean> IN_FILTER_ASPECT = ThreadLocal.withInitial(() -> false);

    @Around("transactionOrServiceExecution()")
    public Object applyTenantFilter(ProceedingJoinPoint pjp) throws Throwable {
        if (Boolean.TRUE.equals(IN_FILTER_ASPECT.get())) {
            return pjp.proceed();
        }

        IN_FILTER_ASPECT.set(true);
        try {
            Session session = entityManager.unwrap(Session.class);

            // SECURITY BYPASS: SuperAdmin should NEVER be filtered.
            if (isSuperAdmin()) {
                log.trace("🔓 Tenant Isolation BYPASSED for Super Admin (Global View)");
                return pjp.proceed();
            }

            // Obtenemos el ID del tenant (String) desde el contexto
            String tenantId = TenantContext.getTenant();

            if (tenantId != null) {
                session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
                log.trace("🛡️ Tenant Isolation ACTIVE for TenantID: {}", tenantId);
            } else {
                // Zero Trust Mode: Empty context filters everything
                session.enableFilter("tenantFilter").setParameter("tenantId", "NONE");
                log.trace("🔒 Tenant Isolation ACTIVE (Zero Trust Mode) on: {}", pjp.getSignature().getDeclaringTypeName());
            }

            return pjp.proceed();

        } finally {
            try {
                Session session = entityManager.unwrap(Session.class);
                session.disableFilter("tenantFilter");
            } catch (Exception e) {
                // Silently ignore
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
