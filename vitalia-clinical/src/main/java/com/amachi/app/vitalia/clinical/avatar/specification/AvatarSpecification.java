package com.amachi.app.vitalia.clinical.avatar.specification;

import com.amachi.app.vitalia.clinical.avatar.dto.search.AvatarSearchDto;
import com.amachi.app.vitalia.clinical.avatar.entity.Avatar;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AvatarSpecification implements Specification<Avatar> {

    private final AvatarSearchDto searchDto;

    @Override
    public Predicate toPredicate(@NonNull Root<Avatar> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchDto.getUserId() != null) {
            predicates.add(cb.equal(root.get("userId"), searchDto.getUserId()));
        }

        if (searchDto.getTenantCode() != null && !searchDto.getTenantCode().isBlank()) {
            predicates.add(cb.equal(root.get("tenantCode"), searchDto.getTenantCode()));
        }

        // Default sort by created date descending if no specific sort is required
        query.orderBy(cb.desc(root.get("createdDate")));

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
