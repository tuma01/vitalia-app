package com.amachi.app.core.common.service;

import com.amachi.app.core.common.context.TenantContext;
import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.core.common.entity.BaseEntity;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.common.entity.TenantScoped;
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Servicio base enterprise para toda la aplicación.
 * Fixed: Implementing GenericService and fixing update() signature for compatibility.
 */
@Transactional
public abstract class BaseService<E extends BaseEntity, F extends BaseSearchDto> implements GenericService<E, F> {

    protected abstract CommonRepository<E, Long> getRepository();
    protected abstract Specification<E> buildSpecification(F searchDto);
    protected abstract DomainEventPublisher getEventPublisher();

    @Override
    @Transactional(readOnly = true)
    public List<E> getAll() {
        return getRepository().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<E> getAll(F searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return getRepository().findAll(buildSpecification(searchDto), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public E getById(Long id) {
        requireNonNull(id, "ID must not be null");
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity", "err.not_found", id));

        // SaaS Elite Tier: Solo aplicamos aislamiento si la entidad es de tipo TenantScoped
        // Las entidades Globales (como Geography o Medical Catalog) ignoran este check.
        if (entity instanceof TenantScoped tenantScoped) {
            String currentTenant = TenantContext.getTenant();
            if (currentTenant != null && !currentTenant.equals(tenantScoped.getTenantId())) {
                throw new ResourceNotFoundException("Entity", "err.not_found", id);
            }
        }

        return entity;
    }

    protected abstract void publishCreatedEvent(E entity);
    protected abstract void publishUpdatedEvent(E entity);
    protected void publishDeletedEvent(E entity) {}

    @Override
    public E create(E entity) {
        requireNonNull(entity, "Entity must not be null");
        E savedEntity = getRepository().save(entity);
        publishCreatedEvent(savedEntity);
        return savedEntity;
    }

    @Override
    public E update(Long id, E entity) {
        requireNonNull(id, "ID must not be null");
        requireNonNull(entity, "Entity must not be null");

        // Ensure entity ID matches path ID
        entity.setId(id);

        // Security Check & Existence
        getById(id);

        E savedEntity = getRepository().save(entity);
        publishUpdatedEvent(savedEntity);
        return savedEntity;
    }

    @Override
    public void delete(Long id) {
        E entity = getById(id);
        if (entity instanceof SoftDeletable soft) {
            soft.setIsDeleted(true);
            getRepository().save(entity);
        } else {
            getRepository().delete(entity);
        }
    }
}
