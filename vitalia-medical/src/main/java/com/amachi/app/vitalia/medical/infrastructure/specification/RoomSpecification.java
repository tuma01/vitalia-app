package com.amachi.app.vitalia.medical.infrastructure.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.RoomSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Room;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda ÉLITE para infraestructura de habitaciones.
 */
public class RoomSpecification extends BaseSpecification<Room> {

    private final RoomSearchDto criteria;

    public RoomSpecification(RoomSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            String q = "%" + criteria.getQuery().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("roomNumber")), q),
                cb.like(cb.lower(root.get("description")), q)
            ));
        }

        if (criteria.getUnitId() != null) {
            predicates.add(cb.equal(root.get("unit").get("id"), criteria.getUnitId()));
        }

        if (criteria.getBlockCode() != null && !criteria.getBlockCode().isBlank()) {
            predicates.add(cb.equal(root.get("blockCode"), criteria.getBlockCode()));
        }

        if (criteria.getTypeRoom() != null) {
            predicates.add(cb.equal(root.get("typeRoom"), criteria.getTypeRoom()));
        }

        if (criteria.getCleaningStatus() != null) {
            predicates.add(cb.equal(root.get("cleaningStatus"), criteria.getCleaningStatus()));
        }

        if (criteria.getIsPrivate() != null) {
            predicates.add(cb.equal(root.get("privateRoom"), criteria.getIsPrivate()));
        }

        if (criteria.getActive() != null) {
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
