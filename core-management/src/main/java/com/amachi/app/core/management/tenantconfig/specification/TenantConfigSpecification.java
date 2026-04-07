package com.amachi.app.core.management.tenantconfig.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.core.management.tenantconfig.dto.search.TenantConfigSearchDto;
import com.amachi.app.core.management.tenantconfig.entity.TenantConfig;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TenantConfigSpecification extends BaseSpecification<TenantConfig> {
    private final TenantConfigSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<TenantConfig> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(buildBasePredicates(root, cb)); // ✅ Consistent isolation

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getFallbackHeader() != null && !criteria.getFallbackHeader().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("fallbackHeader")), 
                    "%" + criteria.getFallbackHeader().toLowerCase() + "%"));
        }

        if (criteria.getDefaultDomain() != null && !criteria.getDefaultDomain().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("defaultDomain")), 
                    "%" + criteria.getDefaultDomain().toLowerCase() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
