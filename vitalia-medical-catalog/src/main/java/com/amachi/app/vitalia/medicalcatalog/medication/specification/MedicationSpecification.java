package com.amachi.app.vitalia.medicalcatalog.medication.specification;

import com.amachi.app.vitalia.medicalcatalog.medication.dto.search.MedicationSearchDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MedicationSpecification implements Specification<Medication> {

    private transient MedicationSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<Medication> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getGenericName() != null && !criteria.getGenericName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("genericName")), "%" + criteria.getGenericName().toLowerCase() + "%"));
        }

        if (criteria.getCommercialName() != null && !criteria.getCommercialName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("commercialName")), "%" + criteria.getCommercialName().toLowerCase() + "%"));
        }

        if (criteria.getPharmaceuticalForm() != null && !criteria.getPharmaceuticalForm().isBlank()) {
            predicates.add(cb.equal(root.get("pharmaceuticalForm"), criteria.getPharmaceuticalForm()));
        }

        if (criteria.getActive() != null) {
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
