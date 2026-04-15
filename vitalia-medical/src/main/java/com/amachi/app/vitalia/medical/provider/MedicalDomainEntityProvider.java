package com.amachi.app.vitalia.medical.provider;

import com.amachi.app.core.common.enums.DomainContext;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.person.service.DomainEntityProvider;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.doctor.repository.DoctorRepository;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.vitalia.medical.employee.repository.EmployeeRepository;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import com.amachi.app.vitalia.medical.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Proveedor de entidades clínicas para Vitalia Medical (SaaS Elite Tier).
 * Orquesta la creación de Doctor, Patient, Nurse y Employee.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalDomainEntityProvider implements DomainEntityProvider {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public boolean supports(DomainContext context) {
        return context == DomainContext.DOCTOR || 
               context == DomainContext.PATIENT || 
               context == DomainContext.NURSE || 
               context == DomainContext.EMPLOYEE;
    }

    @Override
    public void createEntity(Person person, Tenant tenant, DomainContext context) {
        log.info("[MEDICAL-PROVIDER] Creating clinical entity for context: {}", context);

        switch (context) {
            case DOCTOR -> createDoctor(person, tenant);
            case PATIENT -> createPatient(person, tenant);
            case EMPLOYEE, NURSE -> createEmployee(person, tenant);
            default -> throw new IllegalArgumentException("Unsupported context: " + context);
        }
    }

    @Override
    public boolean exists(Person person, Tenant tenant, DomainContext context) {
        return switch (context) {
            case DOCTOR -> doctorRepository.existsByPersonAndTenant(person, tenant);
            case PATIENT -> patientRepository.existsByPersonAndTenant(person, tenant);
            case EMPLOYEE, NURSE -> employeeRepository.existsByPersonAndTenant(person, tenant);
            default -> false;
        };
    }

    private void createDoctor(Person person, Tenant tenant) {
        Doctor doctor = Doctor.builder()
                .person(person)
                .tenant(tenant)
                .tenantId(tenant.getCode())
                .isAvailable(true)
                .active(true)
                .build();
        doctorRepository.save(doctor);
    }

    private void createPatient(Person person, Tenant tenant) {
        Patient patient = Patient.builder()
                .person(person)
                .tenantId(tenant.getCode())
                .nhc("NHC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .registrationDate(OffsetDateTime.now())
                .active(true)
                .build();
        patientRepository.save(patient);
    }

    private void createEmployee(Person person, Tenant tenant) {
        Employee employee = Employee.builder()
                .person(person)
                .tenantId(tenant.getCode())
                .active(true)
                .build();
        employeeRepository.save(employee);
    }
}
