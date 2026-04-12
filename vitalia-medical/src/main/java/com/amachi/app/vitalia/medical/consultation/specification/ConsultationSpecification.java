package com.amachi.app.vitalia.medical.consultation.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.consultation.dto.search.ConsultationSearchDto;
import com.amachi.app.vitalia.medical.consultation.entity.Consultation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda dinámico para Consultas (SaaS Elite Tier).
 */
public class ConsultationSpecification extends BaseSpecification<Consultation> {

    private final ConsultationSearchDto searchDto;

    public ConsultationSpecification(ConsultationSearchDto searchDto) {
        this.searchDto = searchDto;
    }

    @Override
    public Predicate toPredicate(Root<Consultation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Agregar predicates base (SaaS isolation)
        predicates.addAll(buildBasePredicates(root, cb));

        if (searchDto != null) {
            if (searchDto.getMedicalHistoryId() != null) {
                predicates.add(cb.equal(root.get("medicalHistory").get("id"), searchDto.getMedicalHistoryId()));
            }
            if (searchDto.getDoctorId() != null) {
                predicates.add(cb.equal(root.get("doctor").get("id"), searchDto.getDoctorId()));
            }
            if (searchDto.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), searchDto.getStatus()));
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
