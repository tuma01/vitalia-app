package com.amachi.app.vitalia.medical.employee.specification;

import com.amachi.app.vitalia.medical.employee.dto.search.EmployeeSearchDto;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeSpecification implements Specification<Employee> {

    private final EmployeeSearchDto searchDto;

    @Override
    public jakarta.persistence.criteria.Predicate toPredicate(
            jakarta.persistence.criteria.Root<Employee> root,
            jakarta.persistence.criteria.CriteriaQuery<?> query,
            jakarta.persistence.criteria.CriteriaBuilder cb) {
        
        List<Predicate> predicates = new ArrayList<>();

        if (searchDto.getNombre() != null && !searchDto.getNombre().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + searchDto.getNombre().toLowerCase() + "%"));
        }

        if (searchDto.getApellidoPaterno() != null && !searchDto.getApellidoPaterno().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("apellidoPaterno")), "%" + searchDto.getApellidoPaterno().toLowerCase() + "%"));
        }

        if (searchDto.getNationalId() != null && !searchDto.getNationalId().isEmpty()) {
            predicates.add(cb.equal(root.get("nationalId"), searchDto.getNationalId()));
        }

        if (searchDto.getEmployeeType() != null) {
            predicates.add(cb.equal(root.get("employeeType"), searchDto.getEmployeeType()));
        }

        if (searchDto.getEmployeeStatus() != null) {
            predicates.add(cb.equal(root.get("employeeStatus"), searchDto.getEmployeeStatus()));
        }

        if (searchDto.getCodigoEmpleado() != null && !searchDto.getCodigoEmpleado().isEmpty()) {
            predicates.add(cb.equal(root.get("codigoEmpleado"), searchDto.getCodigoEmpleado()));
        }

        // Soft Delete filter (if not handled by @Filter)
        predicates.add(cb.equal(root.get("deleted"), false));

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
