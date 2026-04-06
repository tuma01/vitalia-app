package com.amachi.app.vitalia.medicalcatalog.medication.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.search.MedicationSearchDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MedicationSpecification extends BaseSpecification<Medication> {
    private final MedicationSearchDto criteria;

    public MedicationSpecification(MedicationSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Medication> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(buildBasePredicates(root, cb)); // ✅ Isolation

        // Filtrar por ID exacto
        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        // Filtrar por nombre genérico del medicamento (LIKE)
        if (criteria.getGenericName() != null && !criteria.getGenericName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("genericName")), 
                    "%" + criteria.getGenericName().toLowerCase() + "%"));
        }

        // Filtrar por nombre comercial del medicamento (LIKE)
        if (criteria.getCommercialName() != null && !criteria.getCommercialName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("commercialName")),
                    "%" + criteria.getCommercialName().toLowerCase() + "%"));
        }

        // Filtrar por forma farmacéutica (exacto)
        if (criteria.getPharmaceuticalForm() != null && !criteria.getPharmaceuticalForm().isBlank()) {
            predicates.add(cb.equal(root.get("pharmaceuticalForm"), criteria.getPharmaceuticalForm()));
        }

        // Filtrar por estado activo/inactivo
        if (criteria.getActive() != null) {
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
