package com.amachi.app.core.auth.specification;

import com.amachi.app.core.auth.dto.search.RoleSearchDto;
import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.core.common.utils.AppConstants;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RoleSpecification extends BaseSpecification<Role> {

    private final RoleSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(buildBasePredicates(root, cb)); // ✅ Consistent systemic check

        if (criteria == null) {
            return cb.and(predicates.toArray(new Predicate[0]));
        }

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getName() != null && !criteria.getName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")), 
                    "%" + criteria.getName().toLowerCase() + "%"));
        }

        // 🛡️ Security: Only SuperAdmin sees ROLE_SUPER_ADMIN
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isSuperAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(AppConstants.Roles.ROLE_SUPER_ADMIN));

        if (!isSuperAdmin) {
            predicates.add(cb.notEqual(root.get("name"), AppConstants.Roles.ROLE_SUPER_ADMIN));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
