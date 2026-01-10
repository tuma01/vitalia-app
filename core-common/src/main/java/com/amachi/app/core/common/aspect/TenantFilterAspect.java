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

    @Around("transactionOrServiceExecution()")
    public Object applyTenantFilter(ProceedingJoinPoint pjp) throws Throwable {
        // Obtenemos la sesión de Hibernate
        Session session = entityManager.unwrap(Session.class);

        try {
            // Zero Trust: Por defecto habilitamos con ID inválido (-1)
            // Solo si hay contexto válido, usamos el ID real.
            if (TenantContext.getTenantId().isPresent()) {
                Long tenantId = TenantContext.getTenantId().get();
                session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
                log.trace("🛡️ Tenant Isolation ACTIVE for TenantID: {}", tenantId);
            } else {
                // -------------------------------------------------------------
                // MODIFICACIÓN: Si es SuperAdmin, NO activamos el filtro (Bypass)
                // -------------------------------------------------------------
                if (isSuperAdmin()) {
                    log.info("🔓 Tenant Isolation BYPASSED for Super Admin (Global View)");
                } else {
                    // "No Context" = "No Data" (Excepto para Global/System calls que explicitamente
                    // deban ser 'ignorant')
                    // Para proteger SuperAdmin sin tenant seleccionado:
                    session.enableFilter("tenantFilter").setParameter("tenantId", -1L);
                    log.trace("🔒 Tenant Isolation ACTIVE (Zero Trust Mode): No Tenant Context -> Showing Empty Data");
                }
            }

            return pjp.proceed();

        } finally {
            // 🔒 Limpieza obligatoria: Evitar contaminación de sesiones reutilizadas
            session.disableFilter("tenantFilter");
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
