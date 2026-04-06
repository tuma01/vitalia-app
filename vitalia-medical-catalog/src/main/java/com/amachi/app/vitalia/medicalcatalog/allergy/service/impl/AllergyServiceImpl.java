package com.amachi.app.vitalia.medicalcatalog.allergy.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.allergy.dto.search.AllergySearchDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import com.amachi.app.vitalia.medicalcatalog.allergy.event.AllergyCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.allergy.repository.AllergyRepository;
import com.amachi.app.vitalia.medicalcatalog.allergy.specification.AllergySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllergyServiceImpl extends BaseService<Allergy, AllergySearchDto> {

    private final AllergyRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Allergy, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Allergy> buildSpecification(AllergySearchDto searchDto) {
        return new AllergySpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Allergy entity) {
        eventPublisher.publish(new AllergyCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(Allergy entity) {
        // No update event yet
    }

    @Override
    public Allergy create(Allergy entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        // No filtramos por TenantContext porque el catálogo es único para la plataforma.
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Allergy code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public Allergy update(Long id, Allergy entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
