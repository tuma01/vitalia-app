package com.amachi.app.vitalia.management.tenantconfig.specification;

import com.amachi.app.vitalia.management.tenantconfig.dto.search.TenantConfigSearchDto;
import com.amachi.app.vitalia.management.tenantconfig.entity.TenantConfig;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TenantConfigSpecification implements Specification<TenantConfig> {
    private transient TenantConfigSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<TenantConfig> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getFallbackHeader() != null && !criteria.getFallbackHeader().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("fallbackHeader")), "%" + criteria.getFallbackHeader() + "%"));
        }

        if (criteria.getDefaultDomain() != null && !criteria.getDefaultDomain().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("defaultDomain")), "%" + criteria.getDefaultDomain() + "%"));
        }

        if (criteria.getTenantId() != null) {
            predicates.add(cb.equal(root.get("tenantId"), criteria.getTenantId()));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
