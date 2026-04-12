package com.amachi.app.vitalia.medical.professional.specification;

import com.amachi.app.vitalia.medical.professional.dto.search.ProfessionalInfoSearchDto;
import com.amachi.app.vitalia.medical.professional.entity.ProfessionalInfo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda dinámico para Trayectoria Profesional.
 */
@AllArgsConstructor
public class ProfessionalInfoSpecification implements Specification<ProfessionalInfo> {

    private final ProfessionalInfoSearchDto searchDto;

    @Override
    public Predicate toPredicate(Root<ProfessionalInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchDto != null) {
            if (searchDto.getId() != null) {
                predicates.add(cb.equal(root.get("id"), searchDto.getId()));
            }

            if (searchDto.getPersonId() != null) {
                predicates.add(cb.equal(root.get("person").get("id"), searchDto.getPersonId()));
            }

            if (searchDto.getProfessionalRoleContext() != null) {
                predicates.add(cb.equal(root.get("professionalRoleContext"), searchDto.getProfessionalRoleContext()));
            }

            if (searchDto.getPosition() != null) {
                predicates.add(cb.like(cb.lower(root.get("position")), "%" + searchDto.getPosition().toLowerCase() + "%"));
            }

            if (searchDto.getDepartment() != null) {
                predicates.add(cb.like(cb.lower(root.get("department")), "%" + searchDto.getDepartment().toLowerCase() + "%"));
            }

            if (searchDto.getIsCurrent() != null) {
                predicates.add(cb.equal(root.get("isCurrent"), searchDto.getIsCurrent()));
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
