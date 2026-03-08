package com.amachi.app.core.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Aspect to automatically enable the Hibernate Soft Delete filter.
 * Intercepts service executions and enables 'deletedFilter'.
 */
@Aspect
@Component
@Slf4j
public class SoftDeleteFilterAspect {

    @PersistenceContext
    private EntityManager entityManager;

    // Intercept service methods or transactional methods
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional) || execution(* com.amachi.app.vitalia..service..*(..))")
    public void serviceExecution() {
    }

    @Around("serviceExecution()")
    public Object applySoftDeleteFilter(ProceedingJoinPoint pjp) throws Throwable {
        Session session = entityManager.unwrap(Session.class);
        
        try {
            // Enable the soft delete filter
            session.enableFilter("deletedFilter");
            log.trace("🛡️ Soft Delete filter ENABLED");
            
            return pjp.proceed();
        } finally {
            // Clean up to prevent filter leakage
            session.disableFilter("deletedFilter");
        }
    }
}
