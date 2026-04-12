package com.amachi.app.vitalia.medical.patient.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.patient.dto.search.PatientSearchDto;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Lógica de búsqueda ÉLITE para Pacientes usando Criteria API.
 * Extiende BaseSpecification para aislamiento automático de inquilinos.
 */
public class PatientSpecification extends BaseSpecification<Patient> {

    private final PatientSearchDto criteria;

    public PatientSpecification(PatientSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        // Filtro de búsqueda textual (Nombre o Apellidos)
        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            String q = "%" + criteria.getQuery().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("firstName")), q),
                cb.like(cb.lower(root.get("lastName")), q),
                cb.like(cb.lower(root.get("secondLastName")), q)
            ));
        }

        // Filtro por EXTERNAL_ID
        if (criteria.getExternalId() != null && !criteria.getExternalId().isBlank()) {
            predicates.add(cb.equal(root.get("externalId"), criteria.getExternalId()));
        }

        // Filtro por Documento de Identidad (nationalId)
        if (criteria.getNationalId() != null && !criteria.getNationalId().isBlank()) {
            predicates.add(cb.equal(root.get("nationalId"), criteria.getNationalId()));
        }

        // Filtro por NHC
        if (criteria.getNhc() != null && !criteria.getNhc().isBlank()) {
             predicates.add(cb.equal(root.get("nhc"), criteria.getNhc()));
        }

        // Filtro por Email
        if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
            predicates.add(cb.equal(cb.lower(root.get("email")), criteria.getEmail().toLowerCase()));
        }

        // Filtro por Estado
        if (criteria.getPatientStatus() != null) {
            predicates.add(cb.equal(root.get("patientStatus"), criteria.getPatientStatus()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
